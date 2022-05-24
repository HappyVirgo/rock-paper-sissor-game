package com.al.qdt.score.qry.services.mappers

import com.al.qdt.score.qry.base.EntityTests
import org.mapstruct.factory.Mappers
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

import static com.al.qdt.common.enums.Player.USER
import static com.al.qdt.common.helpers.Constants.TEST_UUID

@Title("Testing of the ScoreDtoMapper interface")
class ScoreDtoMapperSpec extends Specification implements EntityTests {

    @Subject
    def mapper = Mappers.getMapper ScoreDtoMapper.class

    def 'Testing of the toDto() method'() {
        given: 'Setup test data'
        def score = createScore TEST_UUID, USER

        when: 'Mapped to dto object'
        def scoreDto = mapper.toDto score

        then: 'Mapped successfully'
        assert scoreDto
        assert scoreDto.winner == USER.name() && scoreDto.winner == score.winner.name()
    }
}
