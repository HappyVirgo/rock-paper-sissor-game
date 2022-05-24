package com.al.qdt.rps.qry.api;

import com.al.qdt.common.advices.GlobalRestExceptionHandler;
import com.al.qdt.rps.qry.base.DtoTests;
import com.al.qdt.rps.qry.services.RpsServiceV1;
import lombok.SneakyThrows;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static com.al.qdt.common.helpers.Constants.*;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testing of the RpsControllerV1 class")
@Tag(value = "controller")
class RpsControllerV1WiremockTest implements DtoTests {
    MockMvc mockMvc;

    @Mock
    RpsServiceV1 rpsService;

    @InjectMocks
    RpsControllerV1 rpsController;

    @Captor
    ArgumentCaptor<UUID> idParamArgumentCaptor;

    @Captor
    ArgumentCaptor<String> usernameParamArgumentCaptor;

    @BeforeEach
    void setUp() {
        this.idParamArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
        this.usernameParamArgumentCaptor = ArgumentCaptor.forClass(String.class);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(this.rpsController)
                .addPlaceholderValue("api.version-one", "/v1")
                .addPlaceholderValue("api.endpoint-games", "games")
                .setControllerAdvice(new GlobalRestExceptionHandler())
                .build();
    }

    @SneakyThrows(Exception.class)
    @Test
    @DisplayName("Testing of the all() method")
    void allTest() {
        final var firstGameDto = createGameDto(USERNAME_ONE);
        final var secondGameDto = createGameDto(USERNAME_TWO);

        when(this.rpsService.all()).thenReturn(List.of(firstGameDto, secondGameDto));

        this.mockMvc.perform(get("/v1/games")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8))
                .andDo(print())
                // response validation
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].username")
                        .value(firstGameDto.getUsername()))
                .andExpect(jsonPath("$.[0].hand")
                        .value(firstGameDto.getHand()))
                .andExpect(jsonPath("$.[1].username")
                        .value(secondGameDto.getUsername()))
                .andExpect(jsonPath("$.[1].hand")
                        .value(secondGameDto.getHand()));

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

        this.mockMvc.perform(get("/v1/games/{id}", TEST_UUID)
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
                        .value(gameDto.getHand()));

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

        when(this.rpsService.findByUsername(any(String.class))).thenReturn(List.of(gameDto));

        this.mockMvc.perform(get("/v1/games/users/{username}", USERNAME_ONE)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8))
                .andDo(print())
                // response validation
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].username")
                        .value(gameDto.getUsername()))
                .andExpect(jsonPath("$.[0].hand")
                        .value(gameDto.getHand()));

        // verify that it was the only invocation and
        // that there's no more unverified interactions
        verify(this.rpsService, only()).findByUsername(this.usernameParamArgumentCaptor.capture());
        assertEquals(USERNAME_ONE, this.usernameParamArgumentCaptor.getValue());
        reset(this.rpsService);
    }
}
