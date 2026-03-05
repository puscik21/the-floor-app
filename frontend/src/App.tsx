import {CssBaseline, ThemeProvider} from '@mui/material';
import {ToastContainer} from "react-toastify";
import {darkTheme} from "./shared/theme/theme.ts";
import GameContent from "./features/game/components/GameContent.tsx";
import {GameContextProvider} from "./context/GameContext.tsx";

function App() {
    return (
        <ThemeProvider theme={darkTheme}>
            <CssBaseline/>
            <GameContextProvider>
                <GameContent/>
            </GameContextProvider>
            <ToastContainer/>
        </ThemeProvider>
    );
}

export default App;
