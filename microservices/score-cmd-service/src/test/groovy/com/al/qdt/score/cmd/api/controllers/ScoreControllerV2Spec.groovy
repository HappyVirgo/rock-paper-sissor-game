package com.al.qdt.score.cmd.api.controllers

import com.al.qdt.common.api.advices.GlobalRestExceptionHandler
import com.al.qdt.score.cmd.domain.services.ScoreServiceV2
import com.google.protobuf.util.JsonFormat
import org.springframework.http.converter.protobuf.ProtobufJsonFormatHttpMessageConverter
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

import static com.al.qdt.common.helpers.Constants.TEST_UUID
import static java.nio.charset.StandardCharsets.UTF_8
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Title("Testing of the ScoreControllerV2 class")
class ScoreControllerV2Spec extends Specification {

    @Shared
    ProtobufJsonFormatHttpMessageConverter protobufJsonFormatHttpMessageConverter

    @Subject
    def scoreService = Mock(ScoreServiceV2)
    MockMvc mockMvc

    // Run before the first feature method
    def setupSpec() {
        def parser = JsonFormat.parser()
                .ignoringUnknownFields()
        def printer = JsonFormat.printer()
                .includingDefaultValueFields()
        protobufJsonFormatHttpMessageConverter = new ProtobufJsonFormatHttpMessageConverter(parser, printer)
    }

    // Run before every feature method
    def setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new ScoreControllerV2(scoreService))
                .addPlaceholderValue("api.version-two", "/v2")
                .addPlaceholderValue("api.endpoint-scores", "scores")
                .setMessageConverters(protobufJsonFormatHttpMessageConverter)
                .setControllerAdvice(new GlobalRestExceptionHandler())
                .build()

        assert mockMvc
    }

    def 'Testing of the deleteById() method'() {
        when: 'Calling the api'
        def result = mockMvc.perform(delete("/v2/scores/{id}", TEST_UUID)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .characterEncoding(UTF_8))
                .andDo(print())

        then: 'Status validation'
        result?.andExpect status().isNoContent()
    }
}
