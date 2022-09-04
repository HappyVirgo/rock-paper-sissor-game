package com.al.qdt.score.cmd.domain.aggregates

import com.al.qdt.score.cmd.base.CommandTests
import com.al.qdt.score.cmd.api.commands.DeleteScoreCommand
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Title("Testing ScoreAggregate class")
class ScoreAggregateSpec extends Specification implements CommandTests {

    @Subject
    ScoreAggregate scoreAggregate
    DeleteScoreCommand deleteScoreCommand

    // Run before every feature method
    void setup() {
        deleteScoreCommand = createDeleteScoreCommand()
        scoreAggregate = new ScoreAggregate(deleteScoreCommand)
    }

    // Run after every feature method
    void cleanup() {
        scoreAggregate = null
    }

    def 'Testing apply method with DeleteScoreCommand class instance'() {
        expect:
        assert scoreAggregate.id == deleteScoreCommand.id
    }
}
