package com.al.qdt.score.qry.api

import com.al.qdt.score.qry.base.AbstractIntegration
import com.al.qdt.score.qry.base.MvcHelper
import spock.lang.Title

import static com.al.qdt.common.enums.Player.USER
import static com.al.qdt.common.helpers.Constants.TEST_UUID
import static java.nio.charset.StandardCharsets.UTF_8
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath

@Title("Integration testing of the ScoreControllerV1 class")
class ScoreControllerV1ITSpec extends AbstractIntegration implements MvcHelper {

    def 'Testing of the all() method'() {
        when: 'Calling the api'
        def result = mockMvc.perform(get("/v1/scores")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8))
                .andDo print()

        then: 'Status and content type validation'
        testStatusAndContentType(result)

        and: 'Etag availability'
        result?.andExpect header().exists("Etag")

        and: 'Response validation'
        result?.andExpect jsonPath('$.[0].winner').exists()
        result?.andExpect jsonPath('$.[0].winner').value(expectedScore.winner.name())
    }

    def 'Testing of the findById() method'() {
        when: 'Calling the api'
        def result = mockMvc.perform(get("/v1/scores/{id}", TEST_UUID)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8))
                .andDo(print())

        then: 'Status and content type validation'
        testStatusAndContentType(result)

        and: 'Etag availability'
        result?.andExpect header().exists("Etag")

        and: 'Response validation'
        result?.andExpect jsonPath('$.winner').exists()
        result?.andExpect jsonPath('$.winner').value(expectedScore.winner.name())
    }

    def 'Testing of the findByWinner() method'() {
        when: 'Calling the api'
        def result = mockMvc.perform(get("/v1/scores/users/{winner}", USER)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8))
                .andDo(print())

        then: 'Status and content type validation'
        testStatusAndContentType(result)

        and: 'Etag availability'
        result?.andExpect header().exists("Etag")

        and: 'Response validation'
        result?.andExpect jsonPath('$.[0].winner').exists()
        result?.andExpect jsonPath('$.[0].winner').value(expectedScore.winner.name())
    }
}
