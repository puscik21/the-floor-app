import { motion } from "framer-motion";
import { styled } from "@mui/material/styles";
import { Box, Button, Tooltip, Typography } from "@mui/material";
import { useGameContext } from "../../../../context/GameContext";

const STATUS_LABEL: Record<string, string> = {
    connected: "🟢 Połączono",
    connecting: "🟡 Łączenie…",
    disconnected: "🔴 Rozłączono",
    error: "🔴 Błąd połączenia",
};

const StartGameButton = () => {
    const { actions, socket } = useGameContext();
    const { socketStatus } = socket;

    const handleClick = () => {
        actions.sendStartGame("Narrator");
        actions.handleStartGame();
    };

    return (
        <Container>
            <motion.div
                initial={{ scale: 0.8, opacity: 0 }}
                animate={{ scale: 1, opacity: 1 }}
                transition={{ delay: 1.4, duration: 0.8 }}
            >
                <StartButton variant="contained" size="large" onClick={handleClick}>
                    Rozpocznij
                </StartButton>
            </motion.div>

            <motion.div
                initial={{ opacity: 0 }}
                animate={{ opacity: 1 }}
                transition={{ delay: 1.8, duration: 0.6 }}
            >
                <Tooltip title="Status połączenia WebSocket">
                    <StatusLabel>{STATUS_LABEL[socketStatus] ?? socketStatus}</StatusLabel>
                </Tooltip>
            </motion.div>
        </Container>
    );
};

export default StartGameButton;

const Container = styled(Box)`
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 12px;
`;

const StartButton = styled(Button)`
    margin-top: 32px;
    padding: 12px 32px;
    font-size: 1.2rem;
    font-weight: bold;
    border-radius: 12px;
    background: linear-gradient(90deg, #007bff, #00b4d8);
    box-shadow: 0 0 20px rgba(0, 150, 255, 0.6);

    &:hover {
        background: linear-gradient(90deg, #0096ff, #00d4ff);
        box-shadow: 0 0 25px rgba(0, 150, 255, 0.9);
    }

    && {
        color: white;
    }
`;

const StatusLabel = styled(Typography)`
    font-size: 0.8rem;
    opacity: 0.6;
    cursor: default;
    user-select: none;
`;

