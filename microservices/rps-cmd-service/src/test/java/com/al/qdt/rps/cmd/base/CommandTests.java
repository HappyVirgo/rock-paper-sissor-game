package com.al.qdt.rps.cmd.base;

import com.al.qdt.common.enums.Hand;
import com.al.qdt.common.enums.Player;
import com.al.qdt.rps.cmd.commands.AddScoreCommand;
import com.al.qdt.rps.cmd.commands.DeleteGameCommand;
import com.al.qdt.rps.cmd.commands.PlayGameCommand;
import com.al.qdt.rps.cmd.commands.RestoreDbCommand;

import java.util.UUID;

public interface CommandTests {

    /**
     * Creates test instance for play game command object.
     *
     * @param id       id of the command
     * @param username username
     * @param hand     user choice
     * @return play game command object
     */
    default PlayGameCommand createPlayGameCommand(UUID id, String username, Hand hand) {
        return PlayGameCommand.builder()
                .id(id)
                .username(username)
                .hand(hand)
                .build();
    }

    /**
     * Creates test instance for add score command object.
     *
     * @param id     id of the command
     * @param winner winner of the round
     * @return add score command object
     */
    default AddScoreCommand createAddScoreCommand(UUID id, Player winner) {
        return AddScoreCommand.builder()
                .id(id)
                .winner(winner)
                .build();
    }

    /**
     * Creates test instance for delete game command object.
     *
     * @param id id of the command
     * @return delete game command object
     */
    default DeleteGameCommand createDeleteGameCommand(UUID id) {
        return DeleteGameCommand.builder()
                .id((id))
                .build();
    }

    /**
     * Creates test instance for restore database command object.
     *
     * @param id id of the command
     * @return restore database command object
     */
    default RestoreDbCommand createRestoreDbCommand(UUID id) {
        return RestoreDbCommand.builder()
                .id(id)
                .build();
    }
}
