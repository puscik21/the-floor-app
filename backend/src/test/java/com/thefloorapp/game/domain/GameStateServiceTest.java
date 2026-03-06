package com.thefloorapp.game.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.thefloorapp.game.api.GameStateDto;
import com.thefloorapp.game.infrastructure.GameStateRepository;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@ExtendWith(MockitoExtension.class)
class GameStateServiceTest {

    @Mock
    private GameStateRepository gameStateRepository;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private GameStateService gameStateService;

    @Nested
    class GetGameState {

        @Test
        void shouldReturnCurrentState() {
            // given
            GameState persistedState = new GameState(GameStateService.GAME_STATE_ID, GameStateValue.INIT);
            when(gameStateRepository.findById(GameStateService.GAME_STATE_ID)).thenReturn(Optional.of(persistedState));

            // then
            assertThat(gameStateService.getGameState().state()).isEqualTo(GameStateValue.INIT);
        }

        @Test
        void shouldInitializeAndSaveRowWhenMissing() {
            // given
            when(gameStateRepository.findById(GameStateService.GAME_STATE_ID)).thenReturn(Optional.empty());
            when(gameStateRepository.save(any(GameState.class))).thenAnswer(invocation -> invocation.getArgument(0));

            // when
            GameStateDto result = gameStateService.getGameState();

            // then
            assertThat(result.state()).isEqualTo(GameStateValue.INIT);
            verify(gameStateRepository).save(new GameState(GameStateService.GAME_STATE_ID, GameStateValue.INIT));
        }
    }

    @Nested
    class UpdateGameState {

        @Test
        void shouldPersistNewState() {
            // given
            when(gameStateRepository.findById(any()))
                .thenReturn(Optional.of(new GameState(GameStateService.GAME_STATE_ID, GameStateValue.INIT)));

            // when
            GameStateDto result = gameStateService.updateGameState(GameStateValue.FLOOR);

            // then
            assertThat(result.state()).isEqualTo(GameStateValue.FLOOR);
            verify(gameStateRepository).save(argThat(state -> state.getState() == GameStateValue.FLOOR));
        }

        @Test
        void shouldBroadcastToWebSocketTopic() {
            // given
            when(gameStateRepository.findById(any()))
                .thenReturn(Optional.of(new GameState(GameStateService.GAME_STATE_ID, GameStateValue.INIT)));

            // when
            gameStateService.updateGameState(GameStateValue.DUEL);

            // then
            verify(messagingTemplate).convertAndSend(
                GameStateService.TOPIC_GAME_STATE,
                new GameStateDto(GameStateValue.DUEL)
            );
        }

        @Test
        void shouldThrowExceptionWhenStateIsNull() {
            assertThatThrownBy(() -> gameStateService.updateGameState(null))
                .isInstanceOf(RuntimeException.class);
        }
    }
}