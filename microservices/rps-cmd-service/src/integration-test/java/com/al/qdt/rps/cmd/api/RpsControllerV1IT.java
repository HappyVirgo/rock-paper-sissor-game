package com.al.qdt.rps.cmd.api;

import com.al.qdt.common.dto.GameDto;
import com.al.qdt.common.enums.Hand;
import com.al.qdt.common.events.rps.GamePlayedEvent;
import com.al.qdt.rps.cmd.base.AbstractIntegrationTest;
import com.al.qdt.rps.cmd.base.DtoTests;
import com.al.qdt.rps.cmd.commands.PlayGameCommand;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.al.qdt.common.enums.Hand.ROCK;
import static com.al.qdt.common.helpers.Constants.TEST_UUID;
import static com.al.qdt.common.helpers.Constants.USERNAME_ONE;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Integration testing of the RpsControllerV1 controller with full context loading")
@Tag(value = "controller")
class RpsControllerV1IT extends AbstractIntegrationTest implements DtoTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    GameDto expectedGameDto;

    @BeforeEach
    public void setUp() {
        this.expectedGameDto = createGameDto(USERNAME_ONE);
        final var playGameCommand = PlayGameCommand.builder()
                .id(TEST_UUID)
                .username(this.expectedGameDto.getUsername())
                .hand(Hand.valueOf(this.expectedGameDto.getHand()))
                .build();
        final var gamePlayedEvent = GamePlayedEvent.builder()
                .id(playGameCommand.getId())
                .username(playGameCommand.getUsername())
                .hand(playGameCommand.getHand())
                .build();
        super.setupEventStore(List.of(gamePlayedEvent));
    }

    @AfterEach
    void clean() {
        this.expectedGameDto = null;
    }

    @Order(1)
    @Test
    @DisplayName("Testing injections")
    void injectionTest() {
        assertNotNull(this.mockMvc);
        assertNotNull(this.objectMapper);
    }

    @Nested
    @DisplayName("Tests for the method play()")
    class Play {

        @SneakyThrows({JsonProcessingException.class, Exception.class})
        @Test
        @DisplayName("Testing of the play() method")
        void playTest() {
            final var json = objectMapper.writeValueAsString(expectedGameDto);

            assertNotNull(json);

            mockMvc.perform(post("/v1/games")
                    .contentType(APPLICATION_JSON)
                    .content(json)
                    .accept(APPLICATION_JSON)
                    .characterEncoding(UTF_8))
                    .andDo(print())
                    // response validation
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.user_choice")
                            .exists())
                    .andExpect(jsonPath("$.user_choice")
                            .value(ROCK.name()))
                    .andExpect(jsonPath("$.machine_choice")
                            .exists())
                    .andExpect(jsonPath("$.result")
                            .exists());

        }
    }

    @Disabled
    @Nested
    @DisplayName("Tests for the method deleteById()")
    class DeleteById {

    }
}
