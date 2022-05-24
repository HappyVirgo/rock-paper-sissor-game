package com.al.qdt.rps.qry.services.mappers;

import com.al.qdt.rps.qry.base.AbstractIntegrationTest;
import com.al.qdt.rps.qry.base.EventTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.al.qdt.common.enums.Hand.ROCK;
import static com.al.qdt.common.helpers.Constants.TEST_UUID;
import static com.al.qdt.common.helpers.Constants.USERNAME_ONE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Integration testing of the GameMapper class")
@Tag(value = "mapper")
class GameMapperIT extends AbstractIntegrationTest implements EventTests {

    @Autowired
    GameMapper gameMapper;

    @Test
    @DisplayName("Testing injections")
    void injectionTest() {
        assertNotNull(this.gameMapper);
    }

    @Test
    @DisplayName("Testing of the toEntity() method")
    void toEntityTest() {
        final var gamePlayedEvent = createGamePlayedEvent();

        final var game = this.gameMapper.toEntity(gamePlayedEvent);

        assertNotNull(game);
        assertEquals(TEST_UUID, game.getId());
        assertEquals(gamePlayedEvent.getId(), game.getId());
        assertEquals(USERNAME_ONE, game.getUsername());
        assertEquals(gamePlayedEvent.getUsername(), game.getUsername());
        assertEquals(ROCK, game.getHand());
        assertEquals(gamePlayedEvent.getHand(), game.getHand());
    }
}
