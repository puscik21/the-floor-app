// TODO: move to shared
export interface Player {
    name: string;
    category: string;
    isPlaying: boolean;
    winStreak: number;
    duelsWon: number;
    timeBoostsAvailable: number;
    timeBoostsUsed: number;
}

