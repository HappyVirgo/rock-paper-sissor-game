package com.al.qdt.score.qry.api

import com.al.qdt.common.advices.GlobalRestExceptionHandler
import com.al.qdt.rps.grpc.v1.services.ListOfScoresResponse
import com.al.qdt.score.qry.base.MvcHelper
import com.al.qdt.score.qry.base.ProtoTests
import com.al.qdt.score.qry.services.ScoreServiceV2
import com.google.protobuf.util.JsonFormat
import org.springframework.http.converter.protobuf.ProtobufJsonFormatHttpMessageConverter
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

import static com.al.qdt.rps.grpc.v1.common.Player.USER
import static com.al.qdt.common.helpers.Constants.TEST_UUID
import static java.nio.charset.StandardCharsets.UTF_8
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath

@Title("Testing of the ScoreControllerV2 class")
class ScoreControllerV2Spec extends Specification implements ProtoTests, MvcHelper {

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

    def 'Testing of the all() method'() {
        given: 'Setup test data'
        def firstScoreDto = createScoreDto()
        def secondScoreDto = createScoreDto()
        def listOfScoresResponse = ListOfScoresResponse.newBuilder()
                .addAllScores([firstScoreDto, secondScoreDto])
                .build()

        and: 'Mock returns list of scores if invoked with no argument'
        scoreService.all() >> listOfScoresResponse

        when: 'Calling the api'
        def result = mockMvc.perform(get("/v2/scores")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8))
                .andDo print()

        then: 'Status and content type validation'
        testStatusAndContentType(result)

        and: 'Response validation'
        result?.andExpect jsonPath('$.scores').exists()
        result?.andExpect jsonPath('$.scores[0].winner').value(firstScoreDto.winner)
        result?.andExpect jsonPath('$.scores[1].winner').value(secondScoreDto.winner)
    }

    def 'Testing of the findById() method'() {
        given: 'Setup test data'
        def scoreDto = createScoreDto()

        and: 'Mock returns a scores if invoked with id argument'
        scoreService.findById(TEST_UUID) >> scoreDto

        when: 'Calling the api'
        def result = mockMvc.perform(get("/v2/scores/{id}", TEST_UUID)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8))
                .andDo(print())

        then: 'Status and content type validation'
        testStatusAndContentType(result)

        and: 'Response validation'
        result?.andExpect jsonPath('$.winner').exists()
        result?.andExpect jsonPath('$.winner').value(scoreDto.winner)
    }

    void 'Testing of the findByWinner() method'() {
        given: 'Setup test data'
        def scoreDto = createScoreDto()
        def listOfScoresResponse = ListOfScoresResponse.newBuilder()
                .addAllScores([scoreDto])
                .build()

        and: 'Mock returns list of scores if invoked with winner type argument'
        scoreService.findByWinner(USER) >> listOfScoresResponse

        when: 'Calling the api'
        def result = mockMvc.perform(get("/v2/scores/users/{winner}", USER)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8))
                .andDo(print())

        then: 'Status and content type validation'
        testStatusAndContentType(result)

        and: 'Response validation'
        result?.andExpect jsonPath('$.scores').exists()
        result?.andExpect jsonPath('$.scores[0].winner').value(scoreDto.winner)
    }
}
