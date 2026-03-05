import { Box, Container, GlobalStyles } from "@mui/material";
import { useGameContext } from "../../../context/GameContext";
import GameScreen from "../../duel/components/GameScreen";
import FloorScreen from "./floor/FloorScreen";
import WelcomeScreen from "./welcome/WelcomeScreen";
import PodiumScreen from "../../duel/components/podium/PodiumScreen";
import FinishedDuelScreen from "../../duel/components/finish/FinishedDuelScreen";
import { styled } from "@mui/material/styles";

const GameContent = () => {
    const gameState = useGameContext().general.gameState;

    const renderContent = () => {
        switch (gameState) {
            case "ready":
            case "duel":
                return <GameScreen />;
            case "finished":
                return <FinishedDuelScreen />;
            case "floor":
                return <FloorScreen />;
            case "podium":
                return <PodiumScreen />;
            case "init":
            default:
                return <WelcomeScreen />;
        }
    };

    const globalStyles = {
        html: { height: "100%" },
        body: { margin: 0, padding: 0, height: "100%", overflow: "hidden" },
        "#root": { height: "100%" },
    };

    return (
        <ContentContainer>
            <GlobalStyles styles={globalStyles} />
            <Container maxWidth={false} disableGutters sx={{ textAlign: "center" }}>
                {renderContent()}
            </Container>
        </ContentContainer>
    );
};

export default GameContent;

const ContentContainer = styled(Box)`
    user-select: none;
`;

