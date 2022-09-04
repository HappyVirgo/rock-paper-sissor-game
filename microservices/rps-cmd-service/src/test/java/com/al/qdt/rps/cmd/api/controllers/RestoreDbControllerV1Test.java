package com.al.qdt.rps.cmd.api.controllers;

import com.al.qdt.common.api.advices.GlobalRestExceptionHandler;
import com.al.qdt.common.api.dto.BaseResponseDto;
import com.al.qdt.rps.cmd.api.advices.InvalidUserInputExceptionHandler;
import com.al.qdt.rps.cmd.base.DtoTests;
import com.al.qdt.rps.cmd.domain.services.AdminServiceV1;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.concurrent.CompletableFuture;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testing of the RestoreReadDbControllerV1 class")
@Tag(value = "controller")
class RestoreDbControllerV1Test implements DtoTests {

    @Mock
    AdminServiceV1 adminService;

    @InjectMocks
    RestoreDbControllerV1 restoreReadDbController;

    MockMvc mockMvc;
    BaseResponseDto baseResponse;

    @BeforeEach
    void setUp() {
        this.baseResponse = createBaseResponse();
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(this.restoreReadDbController)
                .addPlaceholderValue("api.version-one", "/v1")
                .addPlaceholderValue("api.version-one-async", "/v1.1")
                .addPlaceholderValue("api.endpoint-admin", "admin")
                .setControllerAdvice(new GlobalRestExceptionHandler())
                .setControllerAdvice(new InvalidUserInputExceptionHandler())
                .build();
    }

    @AfterEach
    void clean() {
        this.baseResponse = null;
    }

    @SneakyThrows({JsonProcessingException.class, Exception.class})
    @Test
    @DisplayName("Testing of the restoreReadDb() method")
    void restoreReadDbTest() {
        when(this.adminService.restoreDb()).thenReturn(baseResponse);

        this.mockMvc.perform(post("/v1/admin")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .characterEncoding(UTF_8))
                .andDo(print())
                // response validation
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.message")
                        .value(this.baseResponse.getMessage()));

        // verify that it was the only invocation and
        // that there's no more unverified interactions
        verify(this.adminService, only()).restoreDb();
        verifyNoMoreInteractions(this.adminService);
        reset(this.adminService);
    }

    @SneakyThrows({JsonProcessingException.class, Exception.class})
    @Test
    @DisplayName("Testing of the restoreReadDbAsync() method")
    void restoreReadDbAsyncTest() {
        final var baseResponseCompletableFuture = CompletableFuture.completedFuture(baseResponse);

        when(this.adminService.restoreDbAsync()).thenReturn(baseResponseCompletableFuture);

        final var mvcResult = mockMvc.perform(post("/v1.1/admin")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .characterEncoding(UTF_8))
                .andExpect(request().asyncStarted())
                .andReturn();

        this.mockMvc.perform(asyncDispatch(mvcResult))
                .andDo(print())
                // response validation
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.message")
                        .value(this.baseResponse.getMessage()));

        // verify that it was the only invocation and
        // that there's no more unverified interactions
        verify(this.adminService, only()).restoreDbAsync();
        verifyNoMoreInteractions(this.adminService);
        reset(this.adminService);
    }
}
