package com.al.qdt.score.qry.services.mappers

import com.al.qdt.score.qry.base.AbstractIntegration
import com.al.qdt.score.qry.base.EntityTests
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Subject
import spock.lang.Title

import static com.al.qdt.common.enums.Player.USER
import static com.al.qdt.common.helpers.Constants.TEST_UUID

@Title("Integration testing of the ScoreProtoMapper interface")
class ScoreProtoMapperITSpec extends AbstractIntegration implements EntityTests {

    @Subject
    @Autowired
    ScoreProtoMapper mapper

    def 'Testing injections'() {
        expect:
        assert mapper
    }

    def 'Testing of the toDto() method'() {
        given: 'Setup test data'
        def score = createScore TEST_UUID, USER

        when: 'Mapped to proto object'
        def scoreDto = mapper.toDto score

        then: 'Mapped successfully'
        assert scoreDto
        assert scoreDto.id == TEST_UUID.toString()
        assert scoreDto.id == score.id.toString()
        assert scoreDto.winner == USER.name()
        assert scoreDto.winner == score.winner.name()
    }
}
