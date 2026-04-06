import { CssBaseline, ThemeProvider } from '@mui/material';
import { ToastContainer } from "react-toastify";
import { darkTheme } from "./shared/theme/theme";
import GameContent from "./features/game/components/GameContent";
import { GameContextProvider } from "./context/GameContext";

function App() {
    return (
        <ThemeProvider theme={darkTheme}>
            <CssBaseline />
            <GameContextProvider>
                <GameContent />
            </GameContextProvider>
            <ToastContainer />
        </ThemeProvider>
    );
}

export default App;
