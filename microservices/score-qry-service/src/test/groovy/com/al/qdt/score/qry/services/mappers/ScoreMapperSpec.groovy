package com.al.qdt.score.qry.services.mappers

import com.al.qdt.score.qry.base.EventTests
import org.mapstruct.factory.Mappers
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

import static com.al.qdt.common.enums.Player.USER
import static com.al.qdt.common.helpers.Constants.TEST_UUID

@Title("Testing of the ScoreMapper class")
class ScoreMapperSpec extends Specification implements EventTests {

    @Subject
    def mapper = Mappers.getMapper ScoreMapper.class

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
