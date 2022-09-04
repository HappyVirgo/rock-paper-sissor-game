package com.al.qdt.score.qry.domain.services

import com.al.qdt.common.api.dto.ScoreDto
import com.al.qdt.score.qry.base.AbstractIntegration
import com.al.qdt.score.qry.base.EntityTests
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Subject
import spock.lang.Title

import static com.al.qdt.common.enums.Player.USER
import static com.al.qdt.common.helpers.Constants.TEST_UUID
import static com.al.qdt.score.qry.infrastructure.config.CacheConfig.SCORES_CACHE_NAME
import static com.al.qdt.score.qry.infrastructure.config.CacheConfig.SCORE_CACHE_NAME

@Title("Integration testing of the ScoreServiceV1Impl class")
class ScoreServiceV1ImplITSpec extends AbstractIntegration implements EntityTests {

    @Subject
    @Autowired
    ScoreServiceV1Impl scoreService

    def 'Testing injections'() {
        expect:
        assert scoreService
    }

    def 'Testing caching functionality of the findById() method, scores should be cached'() {
        when: 'Calling the api with right parameter'
        scoreService.findById TEST_UUID

        then: 'No exception thrown'
        noExceptionThrown()

        and: 'Data exists in the cache'
        def actualScore = getCachedScoreDtoById(TEST_UUID, ScoreDto.class, SCORE_CACHE_NAME).get() as ScoreDto
        assert expectedScore.id.toString() == actualScore.id
        assert expectedScore.winner.name() == actualScore.winner

        and:
        "Im-memory cache contains $SCORE_CACHE_NAME cache"
        assert cacheManager.cacheNames.contains(SCORE_CACHE_NAME)
    }

    def 'Testing caching functionality of the all() method, scores should be cached'() {
        when: 'Calling the api'
        def actualScores = scoreService.all()

        then: 'No exception thrown'
        noExceptionThrown()

        and: 'List of data returns from server'
        assert actualScores.size() == EXPECTED_COLLECTION_SIZE

        and: 'Im-memory cache size is not empty'
        assert !isEmpty(cacheManager.getCache(SCORES_CACHE_NAME))
    }

    def 'Testing caching functionality of the findByWinner() method, scores should not be cached'() {
        when: 'Calling the api with right parameter'
        def actualScores = scoreService.findByWinner USER

        then: 'No exception thrown'
        noExceptionThrown()

        and: 'List of data returns from server'
        assert actualScores.size() == EXPECTED_COLLECTION_SIZE

        and: 'Im-memory cache size is empty'
        assert isEmpty(cacheManager.getCache(SCORE_CACHE_NAME))
    }
}
