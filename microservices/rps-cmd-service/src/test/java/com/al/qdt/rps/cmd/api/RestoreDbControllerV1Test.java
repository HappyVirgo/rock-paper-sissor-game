package com.al.qdt.rps.cmd.api;

import com.al.qdt.common.advices.GlobalRestExceptionHandler;
import com.al.qdt.rps.cmd.advices.InvalidUserInputExceptionHandler;
import com.al.qdt.rps.cmd.base.DtoTests;
import com.al.qdt.rps.cmd.services.AdminServiceV1;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.SneakyThrows;
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

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testing of the RestoreReadDbControllerV1 class")
@Tag(value = "controller")
class RestoreDbControllerV1Test implements DtoTests {
    MockMvc mockMvc;

    @Mock
    AdminServiceV1 adminService;

    @InjectMocks
    RestoreDbControllerV1 restoreReadDbController;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(this.restoreReadDbController)
                .addPlaceholderValue("api.version-one", "/v1")
                .addPlaceholderValue("api.endpoint-admin", "admin")
                .setControllerAdvice(new GlobalRestExceptionHandler())
                .setControllerAdvice(new InvalidUserInputExceptionHandler())
                .build();
    }

    @SneakyThrows({JsonProcessingException.class, Exception.class})
    @Test
    @DisplayName("Testing of the restoreReadDb() method")
    void restoreReadDbTest() {
        final var baseResponse = createBaseResponse();

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
                        .value(baseResponse.getMessage()));

        // verify that it was the only invocation and
        // that there's no more unverified interactions
        verify(this.adminService, only()).restoreDb();
        reset(this.adminService);
    }
}
