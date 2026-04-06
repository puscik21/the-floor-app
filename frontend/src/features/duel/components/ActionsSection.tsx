import { Box, Grid } from "@mui/material";
import DuelActions from "./action/DuelActions";
import { useGameContext } from "../../../context/GameContext";
import PlayerTimeBoostSection from "./action/PlayerTimeBoostSection";
import { useAppSelector } from "../../../store/hook";

const ActionsSection = () => {
    const gameState = useAppSelector(state => state.game.gameState)
    const showTimeBoosts = gameState === "ready";
    const { challengerName, defenderName } = useGameContext().duel;

    return (
        <Box>
            <Grid container spacing={2} alignItems="flex-end">
                <Grid item xs={3}>
                    {showTimeBoosts && (
                        <PlayerTimeBoostSection playerName={challengerName} duelPlayer="challenger"/>
                    )}
                </Grid>
                <Grid item xs={6}>
                    <DuelActions/>
                </Grid>
                <Grid item xs={3}>
                    {showTimeBoosts && (
                        <PlayerTimeBoostSection playerName={defenderName} duelPlayer="defender"/>
                    )}
                </Grid>
            </Grid>
        </Box>
    );
};

export default ActionsSection;

