package com.al.qdt.score.qry.base

import com.al.qdt.score.qry.ScoreQryServiceApp
import com.al.qdt.score.qry.domain.Score
import com.al.qdt.score.qry.repositories.ScoreRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.transaction.PlatformTransactionManager
import spock.lang.Specification
import spock.lang.Stepwise

import java.util.concurrent.ConcurrentHashMap

import static com.al.qdt.common.enums.Player.USER
import static com.al.qdt.common.helpers.Constants.TEST_UUID
import static java.util.Optional.ofNullable

/**
 * Trait for integration tests, allows to reduce the amount of test context restarts.
 */
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = [ScoreQryServiceApp.class]
)
// Ensures that the grpc-server is properly shutdown after each test, avoids "port already in use" during tests
@DirtiesContext
@AutoConfigureMockMvc
@Stepwise
@ActiveProfiles("it")
class AbstractIntegration extends Specification implements EntityTests {
    static final EXPECTED_COLLECTION_SIZE = 1 // expected collection size

    @Autowired
    CacheManager cacheManager

    @Autowired
    MockMvc mockMvc

    @Autowired
    ScoreRepository scoreRepository

    @Autowired
    @Qualifier("transactionManager")
    PlatformTransactionManager transactionManager

    Score expectedScore

    // Run before every feature method
    def setup() {
        expectedScore = createScore TEST_UUID, USER
        scoreRepository.save expectedScore
    }

    // Run after every feature method
    def cleanup() {
        scoreRepository.deleteAll()
        expectedScore = null
        evictAllCaches()
    }

    def 'Testing injections'() {
        expect:
        assert cacheManager
        assert mockMvc
        assert scoreRepository
    }

    /**
     * Retrieves a cached score data transfer object form in-memory cache by its id.
     *
     * @param uuid of the cached score data transfer object
     * @param clazz object type
     * @param cacheName cache name
     * @return a cached score data transfer object
     */
    def getCachedScoreDtoById(UUID uuid, Class clazz, String cacheName) {
        ofNullable(cacheManager.getCache(cacheName))
                .map(cache -> cache.get(uuid.toString(), clazz))
    }

    /**
     * Clears all caches.
     */
    void evictAllCaches() {
        cacheManager.getCacheNames().each { cacheManager.getCache(it).clear() }
    }

    /**
     * Checks if cache is empty
     *
     * @param cache object
     * @return true if empty, false otherwise
     */
    static boolean isEmpty(Cache cache) {
        def nativeCache = (ConcurrentHashMap<?, ?>) cache.getNativeCache()
        nativeCache.isEmpty()
    }
}
