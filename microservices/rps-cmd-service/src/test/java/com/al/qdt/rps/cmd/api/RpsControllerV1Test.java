package com.al.qdt.rps.cmd.api;

import com.al.qdt.common.advices.GlobalRestExceptionHandler;
import com.al.qdt.common.dto.GameDto;
import com.al.qdt.rps.cmd.advices.InvalidUserInputExceptionHandler;
import com.al.qdt.rps.cmd.base.DtoTests;
import com.al.qdt.rps.cmd.services.RpsServiceV1;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.UUID;

import static com.al.qdt.common.enums.Hand.ROCK;
import static com.al.qdt.common.helpers.Constants.TEST_UUID;
import static com.al.qdt.common.helpers.Constants.USERNAME_ONE;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testing of the RpsControllerV1 controller")
@Tag(value = "controller")
class RpsControllerV1Test implements DtoTests {
    MockMvc mockMvc;

    final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    RpsServiceV1 rpsService;

    @InjectMocks
    RpsControllerV1 rpsController;

    @Captor
    ArgumentCaptor<GameDto> gameDtoArgumentCaptor;

    @Captor
    ArgumentCaptor<UUID> idArgumentCaptor;

    @BeforeEach
    void setUp() {
        this.gameDtoArgumentCaptor = ArgumentCaptor.forClass(GameDto.class);
        this.idArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(this.rpsController)
                .addPlaceholderValue("api.version-one", "/v1")
                .addPlaceholderValue("api.endpoint-games", "games")
                .setControllerAdvice(new GlobalRestExceptionHandler())
                .setControllerAdvice(new InvalidUserInputExceptionHandler())
                .build();
    }

    @Nested
    @DisplayName("Tests for the method play()")
    class Play {

        @SneakyThrows({JsonProcessingException.class, Exception.class})
        @Test
        @DisplayName("Testing of the play() method")
        void playTest() {
            final var gameResponseDto = createGameResponseDto();
            final var gameDto = createGameDto(USERNAME_ONE);

            when(rpsService.play(any(GameDto.class))).thenReturn(gameResponseDto);

            final var json = objectMapper.writeValueAsString(gameDto);

            assertNotNull(json);

            mockMvc.perform(post("/v1/games")
                    .contentType(APPLICATION_JSON)
                    .content(json)
                    .accept(APPLICATION_JSON_VALUE)
                    .characterEncoding(UTF_8))
                    .andDo(print())
                    // response validation
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.user_choice")
                            .value(gameResponseDto.getUserChoice()))
                    .andExpect(jsonPath("$.machine_choice")
                            .value(gameResponseDto.getMachineChoice()))
                    .andExpect(jsonPath("$.result")
                            .value(gameResponseDto.getResult().name()));

            // verify that it was the only invocation and
            // that there's no more unverified interactions
            verify(rpsService, only()).play(gameDtoArgumentCaptor.capture());
            assertEquals(USERNAME_ONE, gameDtoArgumentCaptor.getValue().getUsername());
            assertEquals(ROCK.name(), gameDtoArgumentCaptor.getValue().getHand());
            reset(rpsService);
        }

        @SneakyThrows({JsonProcessingException.class, Exception.class})
        @Test
        @DisplayName("Testing of the play() method with missed username, throws MethodArgumentNotValidException exception")
        void playInvalidParamUsernameMissedTest() {
            final var gameDto = createGameDtoWithMissedUserName();

            final var json = objectMapper.writeValueAsString(gameDto);

            assertNotNull(json);

            mockMvc.perform(post("/v1/games")
                    .contentType(APPLICATION_JSON)
                    .content(json)
                    .accept(APPLICATION_JSON_VALUE)
                    .characterEncoding(UTF_8))
                    .andDo(print())
                    // response validation
                    .andExpect(status().isBadRequest())
                    // field validation
                    .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(MethodArgumentNotValidException.class));
            // verify that it was the only invocation and
            // that there's no more unverified interactions
            verify(rpsService, never()).play(any(GameDto.class));
        }

        @SneakyThrows({JsonProcessingException.class, Exception.class})
        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("Testing of the play() method with null or empty username, throws validation exception")
        void playInvalidParamUsernameNullOrEmptyTest(String username) {
            final var gameDto = createGameDto(username);

            final var json = objectMapper.writeValueAsString(gameDto);

            assertNotNull(json);

            mockMvc.perform(post("/v1/games")
                    .contentType(APPLICATION_JSON)
                    .content(json)
                    .accept(APPLICATION_JSON_VALUE)
                    .characterEncoding(UTF_8))
                    .andDo(print())
                    // response validation
                    .andExpect(status().isBadRequest());

            // verify that it was the only invocation and
            // that there's no more unverified interactions
            verify(rpsService, never()).play(any(GameDto.class));
        }
    }

    @Nested
    @DisplayName("Tests for the method deleteById()")
    class DeleteById {

        @SneakyThrows(Exception.class)
        @Test
        @DisplayName("Testing of the deleteById() method")
        void deleteByIdTest() {
            mockMvc.perform(delete("/v1/games/{id}", TEST_UUID)
                    .contentType(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .characterEncoding(UTF_8))
                    .andDo(print())
                    // response validation
                    .andExpect(status().isNoContent());

            // verify that it was the only invocation and
            // that there's no more unverified interactions
            verify(rpsService, only()).deleteById(idArgumentCaptor.capture());
            assertEquals(TEST_UUID, idArgumentCaptor.getValue());
            reset(rpsService);
        }
    }
}
