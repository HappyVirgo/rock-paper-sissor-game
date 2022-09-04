package com.al.qdt.rps.qry.domain.mappers;

import com.al.qdt.rps.qry.base.EventTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static com.al.qdt.common.enums.Hand.ROCK;
import static com.al.qdt.common.helpers.Constants.TEST_UUID;
import static com.al.qdt.common.helpers.Constants.USERNAME_ONE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Testing of the GameMapper class")
@Tag(value = "mapper")
class GameMapperTest implements EventTests {
    final GameMapper gameMapper = Mappers.getMapper(GameMapper.class);

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
