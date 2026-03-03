import api from "./apiInstance.ts";
import {notifyError} from "../utils/toast/notifier.tsx";
import type {Player} from "../types.ts";

export const fetchPlayers = async (): Promise<Player[]> => {
    try {
        const response = await api.get<Player[]>("/players");
        return response.data;
    } catch (error) {
        notifyError("Error while fetching players", error)
        return [];
    }
}
