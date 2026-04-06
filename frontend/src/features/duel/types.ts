import type { Player } from "../../shared/types";

export type DuelPlayer = "challenger" | "defender";

export type Question = {
    id: number;
    category: string;
    imageUrl: string;
};

export interface DuelInfo {
    challengerTimer: number;
    defenderTimer: number;
    activePlayer: DuelPlayer;
    passTimer: number;
    isPassPenaltyActive: boolean;
    challengerName: string;
    defenderName: string;
    question: Question;
}

export interface PodiumPlayer {
    name: string;
    duelsWon: number;
    timeBoostsUsed: number;
    position: PodiumPosition;
}

export type PodiumPosition = 1 | 2 | 3;

export interface GameDuelStateResult {
    duelInfo: DuelInfo;
    actions: {
        handleCorrectAnswer: () => void;
        handlePass: () => void;
        handleReturnToMap: () => void;
        prepareDuelState: (challenger: Player, defender: Player) => void;
        addTimeBoostsToPlayerTimer: (duelPlayer: DuelPlayer) => void;
    };
}

