package com.al.qdt.score.qry.api

import com.al.qdt.common.advices.GlobalRestExceptionHandler
import com.al.qdt.common.dto.ScoreDto
import com.al.qdt.score.qry.base.MvcHelper
import com.al.qdt.score.qry.services.ScoreServiceV1
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

import static com.al.qdt.common.enums.Player.MACHINE
import static com.al.qdt.common.enums.Player.USER
import static com.al.qdt.common.helpers.Constants.TEST_UUID
import static java.nio.charset.StandardCharsets.UTF_8
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath

@Title("Testing of the ScoreControllerV1 class")
class ScoreControllerV1Spec extends Specification implements MvcHelper {

    @Subject
    def scoreService = Mock(ScoreServiceV1)
    MockMvc mockMvc

    // Run before every feature method
    def setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new ScoreControllerV1(scoreService))
                .addPlaceholderValue("api.version-one", "/v1")
                .addPlaceholderValue("api.endpoint-scores", "scores")
                .setControllerAdvice(new GlobalRestExceptionHandler())
                .build()

        assert mockMvc
    }

    def 'Testing of the all() method'() {
        given: 'Setup test data'
        def firstScoreDto = ScoreDto.builder()
                .winner(USER.name())
                .build()
        def secondScoreDto = ScoreDto.builder()
                .winner(MACHINE.name())
                .build()

        and: 'Mock returns list of scores if invoked with no argument'
        scoreService.all() >> [firstScoreDto, secondScoreDto]

        when: 'Calling the api'
        def result = mockMvc.perform(get("/v1/scores")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8))
                .andDo print()

        then: 'Status and content type validation'
        testStatusAndContentType(result)

        and: 'Response validation'
        result?.andExpect jsonPath('$.[0].winner').exists()
        result?.andExpect jsonPath('$.[0].winner').value(firstScoreDto.winner)
        result?.andExpect jsonPath('$.[1].winner').exists()
        result?.andExpect jsonPath('$.[1].winner').value(secondScoreDto.winner)
    }

    def 'Testing of the findById() method'() {
        given: 'Setup test data'
        def scoreDto = ScoreDto.builder()
                .winner(USER.name())
                .build()

        and: 'Mock returns score if invoked with id argument'
        scoreService.findById(TEST_UUID) >> scoreDto

        when: 'Calling the api'
        def result = mockMvc.perform(get("/v1/scores/{id}", TEST_UUID)
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

    def 'Testing of the findByWinner() method'() {
        given: 'Setup test data'
        def scoreDto = ScoreDto.builder()
                .winner(USER.name())
                .build()

        and: 'Mock returns list of scores if invoked with player type argument'
        scoreService.findByWinner(USER) >> [scoreDto]

        when: 'Calling the api'
        def result = mockMvc.perform(get("/v1/scores/users/{winner}", USER)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8))
                .andDo(print())

        then: 'Status and content type validation'
        testStatusAndContentType(result)

        and: 'Response validation'
        result?.andExpect jsonPath('$.[0].winner').exists()
        result?.andExpect jsonPath('$.[0].winner').value(scoreDto.winner)
    }
}
