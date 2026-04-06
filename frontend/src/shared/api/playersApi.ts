import api from "./apiInstance";
import { notifyError } from "../utils/toast/notifier";
import type { Player } from "../types";

export const fetchPlayers = async (): Promise<Player[]> => {
    try {
        const response = await api.get<Player[]>("/players");
        return response.data;
    } catch (error) {
        notifyError("Error while fetching players", error);
        return [];
    }
};

