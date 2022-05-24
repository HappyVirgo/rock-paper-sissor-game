package com.al.qdt.score.qry.queries

import com.al.qdt.score.qry.base.ValidationBaseTest
import spock.lang.Subject
import spock.lang.Title
import spock.lang.Unroll

import javax.validation.ConstraintViolation

import static com.al.qdt.common.helpers.Constants.USERNAME_ONE
import static com.al.qdt.score.qry.queries.FindScoresByWinnerQuery.WINNER_MUST_NOT_BE_BLANK

@Title("Testing FindScoresByWinnerQuery class")
class FindScoresByWinnerQuerySpec extends ValidationBaseTest {

    @Subject
    FindScoresByWinnerQuery expectedFindScoresByWinnerQuery

    // Run before every feature method
    def setup() {
        expectedFindScoresByWinnerQuery = new FindScoresByWinnerQuery(USERNAME_ONE)
    }

    // Run after every feature method
    def cleanup() {
        expectedFindScoresByWinnerQuery = null
    }

    def 'Testing FindScoresByWinnerQuery properties'() {
        expect:
        assert expectedFindScoresByWinnerQuery.winner == USERNAME_ONE: "Winner didn't match!"
    }

    def 'Testing FindScoresByWinnerQuery equals() and hashCode() methods'() {
        given: 'Setup test data'
        def actualFindScoresByWinnerQuery = new FindScoresByWinnerQuery(USERNAME_ONE)

        expect:
        assert expectedFindScoresByWinnerQuery == actualFindScoresByWinnerQuery &&
                actualFindScoresByWinnerQuery == expectedFindScoresByWinnerQuery

        and:
        assert expectedFindScoresByWinnerQuery.hashCode() == actualFindScoresByWinnerQuery.hashCode()
    }

    @Unroll
    def 'Testing username validating constrains with right parameters = #winner'(String winner) {
        given: 'Setup test data'
        def findScoresByWinnerQuery = new FindScoresByWinnerQuery(winner)

        when: 'Validate test data'
        def constraintViolations = validator.validate findScoresByWinnerQuery

        then:
        assert constraintViolations.size() == ZERO_VIOLATIONS

        where:
        winner       | _
        USERNAME_ONE | _
    }

    @Unroll
    def 'Testing username validating constrains with wrong parameter - #winner'(String winner) {
        given: 'Setup test data'
        def findScoresByWinnerQuery = new FindScoresByWinnerQuery(winner)

        when: 'Validate test data'
        Set<ConstraintViolation<FindScoresByWinnerQuery>> constraintViolations = validator.validate findScoresByWinnerQuery

        then:
        assert constraintViolations.size() == SINGLE_VIOLATION
        assert constraintViolations.iterator().next().message == WINNER_MUST_NOT_BE_BLANK

        where:
        winner | _
        ''     | _
        null   | _
    }
}
