package com.al.qdt.score.cmd.base

import com.al.qdt.score.cmd.commands.DeleteScoreCommand

trait CommandTests {

    /**
     * Creates test instance for delete score command object.
     *
     * @param id id of the command
     * @return delete score command object
     */
    DeleteScoreCommand createDeleteScoreCommand(UUID id) {
        DeleteScoreCommand.builder()
                .id(id)
                .build()
    }
}
