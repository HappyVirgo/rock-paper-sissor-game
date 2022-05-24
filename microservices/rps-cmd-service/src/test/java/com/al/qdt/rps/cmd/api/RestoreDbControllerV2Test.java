package com.al.qdt.rps.cmd.api;

import com.al.qdt.common.advices.GlobalRestExceptionHandler;
import com.al.qdt.rps.cmd.advices.InvalidUserInputExceptionHandler;
import com.al.qdt.rps.cmd.base.ProtoTests;
import com.al.qdt.rps.cmd.services.AdminServiceV2;
import com.al.qdt.rps.grpc.v1.common.BaseResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.protobuf.util.JsonFormat;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.converter.protobuf.ProtobufJsonFormatHttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.concurrent.CompletableFuture;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testing of the RestoreReadDbControllerV2 class")
@Tag(value = "controller")
class RestoreDbControllerV2Test implements ProtoTests {

    private static ProtobufJsonFormatHttpMessageConverter protobufJsonFormatHttpMessageConverter;

    @Mock
    AdminServiceV2 adminService;

    @InjectMocks
    RestoreDbControllerV2 restoreReadDbController;

    MockMvc mockMvc;
    BaseResponseDto baseResponse;

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
        this.baseResponse = createBaseResponseDto();
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(this.restoreReadDbController)
                .addPlaceholderValue("api.version-two", "/v2")
                .addPlaceholderValue("api.version-two-async", "/v2.1")
                .addPlaceholderValue("api.endpoint-admin", "admin")
                .setMessageConverters(protobufJsonFormatHttpMessageConverter)
                .setControllerAdvice(new GlobalRestExceptionHandler())
                .setControllerAdvice(new InvalidUserInputExceptionHandler())
                .build();
    }

    @SneakyThrows({JsonProcessingException.class, Exception.class})
    @Test
    @DisplayName("Testing of the restoreReadDb() method")
    void restoreReadDbTest() {
        when(this.adminService.restoreDb()).thenReturn(baseResponse);

        this.mockMvc.perform(post("/v2/admin")
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

        final var mvcResult = mockMvc.perform(post("/v2.1/admin")
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
