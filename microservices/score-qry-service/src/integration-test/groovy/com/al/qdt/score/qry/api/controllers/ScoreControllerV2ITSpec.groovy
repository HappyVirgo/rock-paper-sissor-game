package com.al.qdt.score.qry.api.controllers

import com.al.qdt.score.qry.base.AbstractIntegration
import com.al.qdt.score.qry.base.EntityTests
import com.al.qdt.score.qry.base.MvcHelper
import spock.lang.Title

import static com.al.qdt.rps.grpc.v1.common.Player.USER
import static com.al.qdt.common.helpers.Constants.TEST_UUID
import static java.nio.charset.StandardCharsets.UTF_8
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath

@Title("Integration testing of the ScoreControllerV2 class")
class ScoreControllerV2ITSpec extends AbstractIntegration implements EntityTests, MvcHelper {

    def 'Testing of the all() method'() {
        when: 'Calling the api'
        def result = mockMvc.perform(get("/v2/scores")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8))
                .andDo print()

        then: 'Status and content type validation'
        testStatusAndContentType result

        and: 'Etag availability'
        result?.andExpect header().exists('Etag')

        and: 'Response validation'
        result?.andExpect jsonPath('$.scores').exists()
        result?.andExpect jsonPath('$.scores[0].id').exists()
        result?.andExpect jsonPath('$.scores[0].id').value(expectedScore.id.toString())
        result?.andExpect jsonPath('$.scores[0].winner').exists()
        result?.andExpect jsonPath('$.scores[0].winner').value(expectedScore.winner.name())
    }

    def 'Testing of the findById() method'() {
        when: 'Calling the api'
        def result = mockMvc.perform(get("/v2/scores/{id}", TEST_UUID)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8))
                .andDo print()

        then: 'Status and content type validation'
        testStatusAndContentType result

        and: 'Etag availability'
        result?.andExpect header().exists('Etag')

        and: 'Response validation'
        result?.andExpect jsonPath('$.id').exists()
        result?.andExpect jsonPath('$.id').value(expectedScore.id.toString())
        result?.andExpect jsonPath('$.winner').exists()
        result?.andExpect jsonPath('$.winner').value(expectedScore.winner.name())
    }

    def 'Testing of the findByWinner() method'() {
        when: 'Calling the api'
        def result = mockMvc.perform(get("/v2/scores/users/{winner}", USER)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8))
                .andDo print()

        then: 'Status and content type validation'
        testStatusAndContentType result

        and: 'Etag availability'
        result?.andExpect header().exists('Etag')

        and: 'Response validation'
        result?.andExpect jsonPath('$.scores').exists()
        result?.andExpect jsonPath('$.scores[0].id').exists()
        result?.andExpect jsonPath('$.scores[0].id').value(expectedScore.id.toString())
        result?.andExpect jsonPath('$.scores[0].winner').exists()
        result?.andExpect jsonPath('$.scores[0].winner').value(expectedScore.winner.name())
    }
}
