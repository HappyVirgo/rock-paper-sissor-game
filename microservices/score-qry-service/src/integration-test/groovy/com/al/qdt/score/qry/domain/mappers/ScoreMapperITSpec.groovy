package com.al.qdt.score.qry.domain.mappers

import com.al.qdt.score.qry.base.AbstractIntegration
import com.al.qdt.score.qry.base.EventTests
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Subject
import spock.lang.Title

import static com.al.qdt.common.enums.Player.USER
import static com.al.qdt.common.helpers.Constants.TEST_UUID

@Title("Integration testing of the ScoreMapper class")
class ScoreMapperITSpec extends AbstractIntegration implements EventTests {

    @Subject
    @Autowired
    ScoreMapper mapper

    def 'Testing injections'() {
        expect:
        assert mapper
    }

    def 'Testing of the toEntity() method'() {
        given: 'Setup test data'
        def scoresAddedEvent = createScoresAddedEvent()

        when: 'Mapped to entity object'
        def score = mapper.toEntity scoresAddedEvent

        then: 'Mapped successfully'
        assert score
        assert score.id == TEST_UUID && score.id == scoresAddedEvent.id
        assert score.winner == USER && score.winner == scoresAddedEvent.winner
    }
}
