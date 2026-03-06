package com.thefloorapp.game.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.thefloorapp.game.domain.GameStateService;
import com.thefloorapp.game.domain.GameStateValue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

@WebMvcTest(GameStateController.class)
class GameStateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private GameStateService gameStateService;

    @Test
    void shouldReturnsCurrentState() throws Exception {
        when(gameStateService.getGameState()).thenReturn(new GameStateDto(GameStateValue.INIT));

        mockMvc.perform(get("/api/game/state"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.state").value("init"));
    }

    @Test
    void shouldAcceptValidStateUpdate() throws Exception {
        when(gameStateService.updateGameState(any())).thenReturn(new GameStateDto(GameStateValue.FLOOR));

        mockMvc.perform(put("/api/game/state")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new GameStateDto(GameStateValue.FLOOR))))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.state").value("floor"));
    }

    @Test
    void shouldReturnBadRequestForUnknownStateUpdate() throws Exception {
        mockMvc.perform(put("/api/game/state")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"state\":\"unknown\"}"))
            .andExpect(status().isBadRequest());
    }
}
