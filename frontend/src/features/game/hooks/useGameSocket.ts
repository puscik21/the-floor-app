import { useCallback, useEffect, useRef, useState } from "react";
import { Client, type IMessage } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import type { GameStartedEvent, SocketStatus, StartGameRequest } from "../types";
import type { GameState } from "../../../shared/types";
import { notifyError, notifySuccess } from "../../../shared/utils/toast/notifier";

const WS_URL = "http://localhost:8080/ws";
const TOPIC_GAME = "/topic/game";
const TOPIC_GAME_STATE = "/topic/game-state";
const DEST_GAME_START = "/app/game.start";

interface GameStateMessage {
    state: GameState;
}

// TODO: use React Redux
const useGameSocket = (onGameStateChange?: (state: GameState) => void) => {
    const [socketStatus, setSocketStatus] = useState<SocketStatus>("disconnected");
    const clientRef = useRef<Client | null>(null);
    const onGameStateChangeRef = useRef(onGameStateChange);

    useEffect(() => {
        onGameStateChangeRef.current = onGameStateChange;
    }, [onGameStateChange]);

    const handleGameStarted = useCallback((message: IMessage) => {
        const event: GameStartedEvent = JSON.parse(message.body);
        notifySuccess(event.message, `Triggered by: ${event.triggeredBy}`);
    }, []);

    const handleGameStateChange = useCallback((message: IMessage) => {
        const {state}: GameStateMessage = JSON.parse(message.body);
        onGameStateChangeRef.current?.(state);
    }, []);

    const connect = useCallback(() => {
        if (clientRef.current?.active) return;

        const client = new Client({
            webSocketFactory: () => new SockJS(WS_URL),
            reconnectDelay: 5000,
            heartbeatIncoming: 4000,
            heartbeatOutgoing: 4000,
        });

        client.onConnect = () => {
            setSocketStatus("connected");
            client.subscribe(TOPIC_GAME, handleGameStarted);
            client.subscribe(TOPIC_GAME_STATE, handleGameStateChange);
        };

        client.onDisconnect = () => setSocketStatus("disconnected");
        client.onStompError = (frame) => {
            setSocketStatus("error");
            notifyError("WebSocket error", frame.headers["message"]);
        };

        client.onWebSocketClose = () => {
            if (client.active) setSocketStatus("connecting");
        };

        clientRef.current = client;
        client.activate();
    }, [handleGameStarted, handleGameStateChange]);

    const disconnect = useCallback(() => {
        clientRef.current?.deactivate();
        clientRef.current = null;
    }, []);

    const sendStartGame = useCallback((playerName = "Narrator") => {
        const client = clientRef.current;

        if (!client?.active) {
            notifyError("WebSocket nie jest połączony");
            return;
        }

        const payload: StartGameRequest = {playerName};
        client.publish({
            destination: DEST_GAME_START,
            body: JSON.stringify(payload),
        });
    }, []);

    // Connect on mount, clean up on unmount
    useEffect(() => {
        connect();
        return () => disconnect();
    }, [connect, disconnect]);

    return {socketStatus, sendStartGame};
};

export default useGameSocket;

