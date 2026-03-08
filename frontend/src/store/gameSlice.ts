import {createSlice, type PayloadAction} from "@reduxjs/toolkit";
import type {GameState} from "../shared/types";
import type {SocketStatus} from "../features/game/types";

interface GameSliceState {
    gameState: GameState;
    socketStatus: SocketStatus;
}

const initialState: GameSliceState = {
    gameState: "init",
    socketStatus: "disconnected",
};

const gameSlice = createSlice({
    name: "game",
    initialState,
    reducers: {
        setGameState: (state, action: PayloadAction<GameState>) => {
            state.gameState = action.payload;
        },
        setSocketStatus: (state, action: PayloadAction<SocketStatus>) => {
            state.socketStatus = action.payload;
        },
    },
});

export const {setGameState, setSocketStatus} = gameSlice.actions;
export default gameSlice.reducer;

