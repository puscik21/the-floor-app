import api from "./apiInstance";
import { notifyError } from "../utils/toast/notifier";
import type { GameState } from "../types";

interface GameStateResponse {
    state: GameState;
}

export const fetchGameState = async (): Promise<GameState> => {
    try {
        const response = await api.get<GameStateResponse>("/game/state");
        return response.data.state;
    } catch (error) {
        notifyError("Error while fetching game state", error);
        return "init";
    }
};

export const updateGameState = async (state: GameState): Promise<void> => {
    try {
        await api.put("/game/state", { state });
    } catch (error) {
        notifyError("Error while updating game state", error);
    }
};

