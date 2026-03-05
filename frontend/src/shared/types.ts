import type { Player } from "../features/player/types";
import type { DuelInfo, DuelPlayer } from "../features/duel/types";
import type { MapState, SocketStatus } from "../features/game/types";
import type { GridCell } from "../features/game/types";

export type { Player } from "../features/player/types";
export type { DuelPlayer } from "../features/duel/types";

export interface GameConfig {
    initTimeSeconds: number;
    passPenaltySeconds: number;
    winStreakForTimeBoost: number;
    imageFileFormat: string;
    correctAnswerButton: string;
    passButton: string;
    shufflePlayers: boolean;
}

export type GameState = "init" | "floor" | "ready" | "duel" | "finished" | "podium";

export interface GeneralState {
    gameState: GameState;
    winner: Player | null;
}

export interface GameActions {
    handleStartGame: () => void;
    handleStartDuel: () => void;
    activateTimeBoostForPlayer: (playerName: string, duelPlayer: DuelPlayer) => void;
    handleReturnToMap: () => void;
    handleCellClick: (cell: GridCell) => void;
    handleCorrectAnswer: () => void;
    handlePass: () => void;
    handlePassFloorClick: () => void;
    findPlayerByName: (name: string) => Player | undefined;
    sendStartGame: (playerName?: string) => void;
}

export interface SocketState {
    socketStatus: SocketStatus;
}

export interface GameContextValue {
    general: GeneralState;
    map: MapState;
    duel: DuelInfo;
    actions: GameActions;
    config: GameConfig;
    socket: SocketState;
}

