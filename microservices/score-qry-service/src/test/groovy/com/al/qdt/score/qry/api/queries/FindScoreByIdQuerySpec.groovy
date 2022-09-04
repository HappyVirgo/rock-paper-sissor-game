package com.al.qdt.score.qry.api.queries

import com.al.qdt.score.qry.base.ValidationBaseTest
import spock.lang.Subject
import spock.lang.Title
import spock.lang.Unroll

import javax.validation.ConstraintViolation

import static com.al.qdt.common.helpers.Constants.TEST_UUID
import static com.al.qdt.score.qry.api.queries.FindScoreByIdQuery.ID_MUST_NOT_BE_NULL

@Title("Testing FindScoreByIdQuery class")
class FindScoreByIdQuerySpec extends ValidationBaseTest {

    @Subject
    FindScoreByIdQuery expectedFindScoreByIdQuery

    // Run before every feature method
    def setup() {
        expectedFindScoreByIdQuery = new FindScoreByIdQuery(TEST_UUID)
    }

    // Run after every feature method
    def cleanup() {
        expectedFindScoreByIdQuery = null
    }

    def 'Testing FindScoreByIdQuery properties'() {
        expect:
        assert expectedFindScoreByIdQuery.id == TEST_UUID: "Id didn't match!"
    }

    def 'Testing FindScoreByIdQuery equals() and hashCode() methods'() {
        given: 'Setup test data'
        def actualFindScoresByIdQuery = new FindScoreByIdQuery(TEST_UUID)

        expect:
        assert expectedFindScoreByIdQuery == actualFindScoresByIdQuery &&
                actualFindScoresByIdQuery == expectedFindScoreByIdQuery

        and:
        assert expectedFindScoreByIdQuery.hashCode() == actualFindScoresByIdQuery.hashCode()
    }

    @Unroll
    def 'Testing identification validating constrains with right parameters = #id'(UUID id) {
        given: 'Setup test data'
        def findScoresByIdQuery = new FindScoreByIdQuery(id)

        when: 'Validate test data'
        def constraintViolations = validator.validate findScoresByIdQuery

        then:
        assert constraintViolations.size() == ZERO_VIOLATIONS

        where:
        id        | _
        TEST_UUID | _
    }

    @Unroll
    def 'Testing identification validating constrains with wrong parameters = #id'(UUID id) {
        given: 'Setup test data'
        def findScoresByIdQuery = new FindScoreByIdQuery(id)

        when: 'Validate test data'
        Set<ConstraintViolation<FindScoreByIdQuery>> constraintViolations = validator.validate findScoresByIdQuery

        then:
        assert constraintViolations.size() == SINGLE_VIOLATION
        assert constraintViolations.iterator().next().message == ID_MUST_NOT_BE_NULL

        where:
        id   | _
        null | _
    }
}
