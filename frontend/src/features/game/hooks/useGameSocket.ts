import { useCallback, useEffect, useRef } from "react";
import { Client, type IMessage } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import type { GameStartedEvent, StartGameRequest } from "../types";
import type { GameState } from "../../../shared/types";
import { notifyError, notifySuccess } from "../../../shared/utils/toast/notifier";
import {useDispatch} from "react-redux";
import {setGameState, setSocketStatus} from "../../../store/gameSlice";

const WS_URL = "http://localhost:8080/ws";
const TOPIC_GAME = "/topic/game";
const TOPIC_GAME_STATE = "/topic/game-state";
const DEST_GAME_START = "/app/game.start";

interface GameStateMessage {
    state: GameState;
}

const useGameSocket = () => {
    const dispatch = useDispatch();
    const clientRef = useRef<Client | null>(null);

    const handleGameStarted = useCallback((message: IMessage) => {
        const event: GameStartedEvent = JSON.parse(message.body);
        notifySuccess(event.message, `Triggered by: ${event.triggeredBy}`);
    }, []);

    const handleGameStateChange = useCallback((message: IMessage) => {
        const {state}: GameStateMessage = JSON.parse(message.body);
        dispatch(setGameState(state));
    }, [dispatch]);

    const connect = useCallback(() => {
        if (clientRef.current?.active) return;

        const client = new Client({
            webSocketFactory: () => new SockJS(WS_URL),
            reconnectDelay: 5000,
            heartbeatIncoming: 4000,
            heartbeatOutgoing: 4000,
        });

        client.onConnect = () => {
            dispatch(setSocketStatus("connected"));
            client.subscribe(TOPIC_GAME, handleGameStarted);
            client.subscribe(TOPIC_GAME_STATE, handleGameStateChange);
        };

        client.onDisconnect = () => dispatch(setSocketStatus("disconnected"));
        client.onStompError = (frame) => {
            dispatch(setSocketStatus("error"));
            notifyError("WebSocket error", frame.headers["message"]);
        };

        client.onWebSocketClose = () => {
            if (client.active) dispatch(setSocketStatus("connecting"));
        };

        clientRef.current = client;
        client.activate();
    }, [dispatch, handleGameStarted, handleGameStateChange]);

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

    return {sendStartGame};
};

export default useGameSocket;
