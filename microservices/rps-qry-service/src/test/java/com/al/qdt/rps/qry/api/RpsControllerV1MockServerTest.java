package com.al.qdt.rps.qry.api;

import com.al.qdt.rps.qry.base.DtoTests;
import com.al.qdt.rps.qry.extensions.WiremockExtension;
import com.al.qdt.rps.qry.services.RpsServiceV1;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.al.qdt.common.helpers.Constants.*;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.StringBody.exact;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockserver.integration.ClientAndProxy.startClientAndProxy;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpClassCallback.callback;
import static org.mockserver.model.HttpForward.forward;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.StringBody.exact;

import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndProxy;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.mockserver.model.HttpForward;
import org.mockserver.verify.VerificationTimes;

@ExtendWith({MockitoExtension.class, WiremockExtension.class})
@DisplayName("Testing of the RpsControllerV1 class")
@Tag(value = "controller")
class RpsControllerV1MockServerTest implements DtoTests {


//    @SneakyThrows(Exception.class)
//    @Test
//    @DisplayName("Testing of the all() method")
//    void allTest() {
//        final var firstGameDto = createGameDto(USERNAME_ONE);
//        final var secondGameDto = createGameDto(USERNAME_TWO);
//
//        when(this.rpsService.all()).thenReturn(List.of(firstGameDto, secondGameDto));
//
//        this.mockMvc.perform(get("/v1/games")
//                .contentType(APPLICATION_JSON)
//                .accept(APPLICATION_JSON_VALUE)
//                .characterEncoding(UTF_8))
//                .andDo(print())
//                // response validation
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(APPLICATION_JSON))
//                .andExpect(jsonPath("$.[0].username")
//                        .value(firstGameDto.getUsername()))
//                .andExpect(jsonPath("$.[0].hand")
//                        .value(firstGameDto.getHand()))
//                .andExpect(jsonPath("$.[1].username")
//                        .value(secondGameDto.getUsername()))
//                .andExpect(jsonPath("$.[1].hand")
//                        .value(secondGameDto.getHand()));
//
//        // verify that it was the only invocation and
//        // that there's no more unverified interactions
//        verify(this.rpsService, only()).all();
//        reset(this.rpsService);
//    }

}
