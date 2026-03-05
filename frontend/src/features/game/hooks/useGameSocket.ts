import { useCallback, useEffect, useRef, useState } from "react";
import { Client, type IMessage } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import type { GameStartedEvent, SocketStatus, StartGameRequest } from "../types";
import { notifyError, notifySuccess } from "../../../shared/utils/toast/notifier";

const WS_URL = "http://localhost:8080/ws";
const TOPIC_GAME = "/topic/game";
const DEST_GAME_START = "/app/game.start";

const useGameSocket = () => {
    const [socketStatus, setSocketStatus] = useState<SocketStatus>("disconnected");
    const clientRef = useRef<Client | null>(null);

    const connect = useCallback(() => {
        if (clientRef.current?.active) return;

        setSocketStatus("connecting");

        clientRef.current = new Client({
            webSocketFactory: () => new SockJS(WS_URL),
            onConnect: () => {
                setSocketStatus("connected");
                clientRef.current?.subscribe(TOPIC_GAME, (message: IMessage) => {
                    const event: GameStartedEvent = JSON.parse(message.body);
                    notifySuccess(event.message, `Triggered by: ${event.triggeredBy}`);
                });
            },
            onDisconnect: () => {
                setSocketStatus("disconnected");
            },
            onStompError: (frame) => {
                setSocketStatus("error");
                notifyError("WebSocket error", frame.headers["message"]);
            },
            reconnectDelay: 5000,
        });

        clientRef.current.activate();
    }, []);

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

        const payload: StartGameRequest = { playerName };
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

    return { socketStatus, sendStartGame };
};

export default useGameSocket;

