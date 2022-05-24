package com.al.qdt.rps.cmd.api;

import com.al.qdt.rps.cmd.base.AbstractIntegrationTest;
import com.al.qdt.rps.cmd.base.ProtoTests;
import com.al.qdt.rps.grpc.v1.common.BaseResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Integration testing of the RestoreReadDbControllerV2 class")
@Tag(value = "controller")
class RestoreDbControllerV2IT extends AbstractIntegrationTest implements ProtoTests {

    @Autowired
    MockMvc mockMvc;

    BaseResponseDto baseResponse;

    @BeforeEach
    void setUp() {
        this.baseResponse = createBaseResponseDto();
    }

    @AfterEach
    void clean() {
        this.baseResponse = null;
    }

    @Order(1)
    @Test
    @DisplayName("Testing injections")
    void injectionTest() {
        assertNotNull(this.mockMvc);
    }

    @SneakyThrows({JsonProcessingException.class, Exception.class})
    @Test
    @DisplayName("Testing of the restoreReadDb() method")
    void restoreReadDbTest() {
        this.mockMvc.perform(post("/v2/admin")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .characterEncoding(UTF_8))
                .andDo(print())
                // response validation
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.message")
                        .value(baseResponse.getMessage()));
    }

    @SneakyThrows({JsonProcessingException.class, Exception.class})
    @Test
    @DisplayName("Testing of the restoreReadDbAsync() method")
    void restoreReadDbAsyncTest() {
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
                        .value(baseResponse.getMessage()));
    }
}
