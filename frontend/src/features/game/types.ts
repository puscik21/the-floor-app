import type { Player } from "../../shared/types";

export type GameGrid = GridCell[][];

export interface GridCell {
    x: number;
    y: number;
    ownerName: string | null;
}

export interface MapState {
    grid: GameGrid;
    allPlayers: Player[];
    activeMapPlayer: Player | null;
    hasWonPreviousDuel: boolean;
    positionToPlayer: Map<number, Player>;
}

export type SocketStatus = "disconnected" | "connecting" | "connected" | "error";

export interface StartGameRequest {
    playerName: string;
}

export interface GameStartedEvent {
    message: string;
    triggeredBy: string;
}

export interface GameMapStateResult {
    mapState: MapState;
    actions: {
        conquerTerritory: (winnerName: string, loserName: string, inheritedCategory: string) => void;
        handleCellClick: (cell: GridCell) => void;
        handlePassFloorClick: () => void;
        decreaseTimeBoostsOfPlayer: (playerName: string) => void;
        findPlayerByName: (name: string) => Player | undefined;
    };
}

