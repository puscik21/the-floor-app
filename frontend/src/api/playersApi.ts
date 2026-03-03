import api from "./apiInstance.ts";
import {notifyError} from "../utils/toast/notifier.tsx";
import type {PlayerBase} from "../types.ts";

// TODO: Use 'Player' type
export const fetchPlayers = async (): Promise<PlayerBase[]> => {
    try {
        const response = await api.get<PlayerBase[]>("/players");
        return response.data;
    } catch (error) {
        notifyError("Error while fetching players", error)
        return [];
    }
}
