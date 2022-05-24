package com.al.qdt.rps.cmd.api;

import com.al.qdt.common.advices.GlobalRestExceptionHandler;
import com.al.qdt.rps.cmd.advices.InvalidUserInputExceptionHandler;
import com.al.qdt.rps.cmd.base.ProtoTests;
import com.al.qdt.rps.cmd.services.RpsServiceV2;
import com.al.qdt.rps.grpc.v1.dto.GameDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.protobuf.util.JsonFormat;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.converter.protobuf.ProtobufJsonFormatHttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static com.al.qdt.common.helpers.Constants.TEST_UUID;
import static com.al.qdt.common.helpers.Constants.USERNAME_ONE;
import static com.al.qdt.rps.grpc.v1.common.Hand.ROCK;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testing of the RpsControllerV2 controller")
@Tag(value = "controller")
class RpsControllerV2Test implements ProtoTests {
    private static ProtobufJsonFormatHttpMessageConverter protobufJsonFormatHttpMessageConverter;

    MockMvc mockMvc;

    @Mock
    RpsServiceV2 rpsService;

    @InjectMocks
    RpsControllerV2 rpsController;

    @Captor
    ArgumentCaptor<GameDto> gameRequestArgumentCaptor;

    @Captor
    ArgumentCaptor<UUID> idArgumentCaptor;

    @BeforeAll
    static void init() {
        final var parser = JsonFormat.parser()
                .ignoringUnknownFields();
        final var printer = JsonFormat.printer()
                .includingDefaultValueFields();
        protobufJsonFormatHttpMessageConverter = new ProtobufJsonFormatHttpMessageConverter(parser, printer);
    }

    @BeforeEach
    void setUp() {
        this.gameRequestArgumentCaptor = ArgumentCaptor.forClass(GameDto.class);
        this.idArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(this.rpsController)
                .addPlaceholderValue("api.version-two", "/v2")
                .addPlaceholderValue("api.endpoint-games", "games")
                .setMessageConverters(protobufJsonFormatHttpMessageConverter)
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
            final var gameResponseDto = createGameResultDto();
            final var gameDto = createGameDto();

            when(rpsService.play(any(GameDto.class))).thenReturn(gameResponseDto);

            final var outputMessage = new MockHttpOutputMessage();
            protobufJsonFormatHttpMessageConverter.write(gameDto, APPLICATION_JSON, outputMessage);
            final var json = outputMessage.getBodyAsString();

            assertNotNull(json);

            mockMvc.perform(post("/v2/games")
                    .contentType(APPLICATION_JSON)
                    .content(json)
                    .accept(APPLICATION_JSON)
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
                            .value(gameResponseDto.getResult()));

            // verify that it was the only invocation and
            // that there's no more unverified interactions
            verify(rpsService).play(gameRequestArgumentCaptor.capture());
            assertEquals(USERNAME_ONE, gameRequestArgumentCaptor.getValue().getUsername());
            assertEquals(ROCK, gameRequestArgumentCaptor.getValue().getHand());
            reset(rpsService);
        }
    }

    @Nested
    @DisplayName("Tests for the method deleteById()")
    class DeleteById {

        @SneakyThrows(Exception.class)
        @Test
        @DisplayName("Testing of the deleteById() method")
        void deleteByIdTest() {
            mockMvc.perform(delete("/v2/games/{id}", TEST_UUID)
                    .contentType(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .characterEncoding(UTF_8))
                    .andDo(print())
                    // response validation
                    .andExpect(status().isNoContent());

            // verify that it was the only invocation and
            // that there's no more unverified interactions
            verify(rpsService).deleteById(idArgumentCaptor.capture());
            assertEquals(TEST_UUID, idArgumentCaptor.getValue());
            reset(rpsService);
        }
    }
}
