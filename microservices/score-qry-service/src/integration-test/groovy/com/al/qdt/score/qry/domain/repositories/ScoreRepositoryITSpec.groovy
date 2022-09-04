package com.al.qdt.score.qry.domain.repositories

import com.al.qdt.score.qry.base.EntityTests
import com.al.qdt.score.qry.infrastructure.config.TestConfig
import com.al.qdt.score.qry.domain.entities.Score
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.test.context.ActiveProfiles
import spock.lang.PendingFeature
import spock.lang.Specification
import spock.lang.Stepwise
import spock.lang.Subject
import spock.lang.Title

import static com.al.qdt.common.enums.Player.USER
import static com.al.qdt.common.helpers.Constants.TEST_UUID

@DataJpaTest
@Import([TestConfig])
@Stepwise
@ActiveProfiles("it")
@Title("Integration testing of the ScoreRepository interface")
class ScoreRepositoryITSpec extends Specification implements EntityTests {

    Score score
    Score expectedScore

    @Subject
    @Autowired
    ScoreRepository scoreRepository

    // Run before every feature method
    def setup() {
        score = createScore TEST_UUID, USER
        expectedScore = scoreRepository.save score

        assert expectedScore
        assert expectedScore.id == TEST_UUID
        assert expectedScore.id == score.id
    }

    // Run after every feature method
    def cleanup() {
        scoreRepository.deleteAll()
        expectedScore = null
    }

    def 'Testing injections'() {
        expect:
        assert scoreRepository
    }

    def 'Testing database init count'() {
        when: 'Counting items in the database'
        def count = scoreRepository.count()

        then: 'No exceptions thrown'
        noExceptionThrown()

        and: 'Count should be one'
        assert count == 1
    }

    def 'Testing getById() method'() {
        when: 'Getting score by id'
        def actualScore = scoreRepository.getById TEST_UUID

        then: 'No exceptions thrown'
        noExceptionThrown()

        and: 'Validating returned score'
        assert actualScore
        assert actualScore.id == TEST_UUID
        assert actualScore.id == score.id
    }

    def 'Testing findByWinner() method'() {
        when: 'Finding scores by winner type'
        def actualScores = scoreRepository.findByWinner USER

        then: 'No exceptions thrown'
        noExceptionThrown()

        and: 'Validating returned collection of scores'
        assert actualScores
        assert actualScores instanceof List<Score>
        assert !actualScores.isEmpty()
        assert actualScores.size() == 1
        assert actualScores[0].id == TEST_UUID
        assert actualScores[0].id == expectedScore.id
        assert actualScores[0].winner == USER
        assert actualScores[0].winner == expectedScore.winner
    }

    def 'Testing deleteById() method'() {
        when: 'Deleting a score by id'
        scoreRepository.deleteById TEST_UUID

        then: 'No exceptions thrown'
        noExceptionThrown()

        when: 'Counting all available scores'
        def count = scoreRepository.count()

        then: 'No exceptions thrown'
        noExceptionThrown()

        and: 'The database is empty'
        assert count == 0
    }

    def 'Testing existsById() method'() {
        when: 'Checking for availability of specified score in database'
        def isExists = scoreRepository.existsById TEST_UUID

        then: 'No exceptions thrown'
        noExceptionThrown()

        and: 'Specified scores exist'
        assert isExists
    }

    def 'Testing uniqueIdentity'() {
        given: 'Create score with the same id'
        def scoreWithSameId = createScore TEST_UUID, USER

        when: 'Trying to persist it to the database'
        scoreRepository.save scoreWithSameId

        then: 'DataIntegrityViolationException thrown'
        thrown DataIntegrityViolationException
    }

    @PendingFeature
    def 'Testing findById() method'() {}
}
