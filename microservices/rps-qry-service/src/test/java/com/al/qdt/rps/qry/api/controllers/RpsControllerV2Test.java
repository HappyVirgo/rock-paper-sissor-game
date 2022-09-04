package com.al.qdt.rps.qry.api.controllers;

import com.al.qdt.common.api.advices.GlobalRestExceptionHandler;
import com.al.qdt.rps.grpc.v1.services.ListOfGamesResponse;
import com.al.qdt.rps.qry.base.ProtoTests;
import com.al.qdt.rps.qry.domain.services.RpsServiceV2;
import com.google.protobuf.StringValue;
import com.google.protobuf.util.JsonFormat;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.converter.protobuf.ProtobufJsonFormatHttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static com.al.qdt.common.helpers.Constants.TEST_UUID;
import static com.al.qdt.common.helpers.Constants.USERNAME_ONE;
import static com.al.qdt.common.helpers.Constants.USERNAME_TWO;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testing of the RpsControllerV2 class")
@Tag(value = "controller")
class RpsControllerV2Test implements ProtoTests {
    private static ProtobufJsonFormatHttpMessageConverter protobufJsonFormatHttpMessageConverter;

    MockMvc mockMvc;

    @Mock
    RpsServiceV2 rpsService;

    @InjectMocks
    RpsControllerV2 rpsController;

    @Captor
    ArgumentCaptor<UUID> idParamArgumentCaptor;

    @Captor
    ArgumentCaptor<StringValue> stringParamArgumentCaptor;

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
        this.idParamArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
        this.stringParamArgumentCaptor = ArgumentCaptor.forClass(StringValue.class);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(this.rpsController)
                .addPlaceholderValue("api.version-two", "/v2")
                .addPlaceholderValue("api.endpoint-games", "games")
                .setMessageConverters(protobufJsonFormatHttpMessageConverter)
                .setControllerAdvice(new GlobalRestExceptionHandler())
                .build();
    }

    @SneakyThrows(Exception.class)
    @Test
    @DisplayName("Testing of the all() method")
    void allTest() {
        final var firstGameDto = createGameDto(USERNAME_ONE);
        final var secondGameDto = createGameDto(USERNAME_TWO);
        final var listOfGamesResponse = ListOfGamesResponse.newBuilder()
                .addGames(firstGameDto)
                .addGames(secondGameDto)
                .build();

        when(this.rpsService.all()).thenReturn(listOfGamesResponse);

        this.mockMvc.perform(get("/v2/games")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8))
                .andDo(print())
                // response validation
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.games")
                        .exists())
                .andExpect(jsonPath("$.games[0].username")
                        .value(firstGameDto.getUsername()))
                .andExpect(jsonPath("$.games[0].hand")
                        .value(firstGameDto.getHand().name()))
                .andExpect(jsonPath("$.games[1].username")
                        .value(secondGameDto.getUsername()))
                .andExpect(jsonPath("$.games[1].hand")
                        .value(secondGameDto.getHand().name()));

        // verify that it was the only invocation and
        // that there's no more unverified interactions
        verify(this.rpsService, only()).all();
        reset(this.rpsService);
    }

    @SneakyThrows(Exception.class)
    @Test
    @DisplayName("Testing of the findById() method")
    void findByIdTest() {
        final var gameDto = createGameDto(USERNAME_ONE);

        when(this.rpsService.findById(any(UUID.class))).thenReturn(gameDto);

        this.mockMvc.perform(get("/v2/games/{id}", TEST_UUID)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8))
                .andDo(print())
                // response validation
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.username")
                        .value(gameDto.getUsername()))
                .andExpect(jsonPath("$.hand")
                        .value(gameDto.getHand().name()));

        // verify that it was the only invocation and
        // that there's no more unverified interactions
        verify(this.rpsService, only()).findById(this.idParamArgumentCaptor.capture());
        assertEquals(TEST_UUID, this.idParamArgumentCaptor.getValue());
        reset(this.rpsService);
    }

    @SneakyThrows(Exception.class)
    @Test
    @DisplayName("Testing of the findByUsername() method")
    void findByUsernameTest() {
        final var gameDto = createGameDto(USERNAME_ONE);
        final var listOfGamesResponse = ListOfGamesResponse.newBuilder()
                .addGames(gameDto)
                .build();

        when(this.rpsService.findByUsername(any(StringValue.class))).thenReturn(listOfGamesResponse);

        this.mockMvc.perform(get("/v2/games/users/{username}", USERNAME_ONE)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8))
                .andDo(print())
                // response validation
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.games")
                        .exists())
                .andExpect(jsonPath("$.games[0].username")
                        .value(gameDto.getUsername()))
                .andExpect(jsonPath("$.games[0].hand")
                        .value(gameDto.getHand().name()));

        // verify that it was the only invocation and
        // that there's no more unverified interactions
        verify(this.rpsService, only()).findByUsername(this.stringParamArgumentCaptor.capture());
        assertEquals(USERNAME_ONE, this.stringParamArgumentCaptor.getValue().getValue());
        reset(this.rpsService);
    }
}
