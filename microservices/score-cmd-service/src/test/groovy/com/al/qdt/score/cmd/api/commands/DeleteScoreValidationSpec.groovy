package com.al.qdt.score.cmd.api.commands

import com.al.qdt.score.cmd.base.CommandTests
import com.al.qdt.score.cmd.base.ValidationBaseTest
import spock.lang.Subject
import spock.lang.Title
import spock.lang.Unroll

import javax.validation.ConstraintViolation

import static com.al.qdt.common.helpers.Constants.TEST_UUID
import static com.al.qdt.cqrs.messages.Message.ID_MUST_NOT_BE_NULL

@Title("Testing DeleteScoreCommand class")
class DeleteScoreValidationSpec extends ValidationBaseTest implements CommandTests {

    @Subject
    DeleteScoreCommand expectedDeleteScoreCommand

    // Run before every feature method
    def setup() {
        expectedDeleteScoreCommand = createDeleteScoreCommand TEST_UUID
    }

    // Run after every feature method
    def cleanup() {
        expectedDeleteScoreCommand = null
    }

    def 'Testing DeleteScoreCommand properties'() {
        expect:
        assert expectedDeleteScoreCommand.id == TEST_UUID: "Id didn't match!"
    }

    def 'Testing DeleteScoreCommand equals() and hashCode() methods'() {
        given: 'Setup test data'
        def actualDeleteScoreCommand = createDeleteScoreCommand()

        expect:
        assert expectedDeleteScoreCommand == actualDeleteScoreCommand &&
                actualDeleteScoreCommand == expectedDeleteScoreCommand

        and:
        assert expectedDeleteScoreCommand.hashCode() == actualDeleteScoreCommand.hashCode()
    }

    @Unroll
    def 'Testing identifier validating constrains with right parameter - #id'(UUID id) {
        given: 'Setup test data'
        def deleteScoreCommand = createDeleteScoreCommand id

        when: 'Validate test data'
        def constraintViolations = validator.validate deleteScoreCommand

        then:
        assert constraintViolations.size() == ZERO_VIOLATIONS

        where:
        id        | _
        TEST_UUID | _
    }

    @Unroll
    def 'Testing identifier validating constrains with wrong parameter - #id'(UUID id) {
        given: 'Setup test data'
        def deleteScoreCommand = createDeleteScoreCommand id

        when: 'Validate test data'
        Set<ConstraintViolation<DeleteScoreCommand>> constraintViolations = validator.validate deleteScoreCommand

        then:
        assert constraintViolations.size() == SINGLE_VIOLATION
        assert constraintViolations.iterator().next().message == ID_MUST_NOT_BE_NULL

        where:
        id   | _
        null | _
    }
}
