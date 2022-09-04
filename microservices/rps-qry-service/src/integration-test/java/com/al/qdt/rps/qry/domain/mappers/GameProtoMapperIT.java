package com.al.qdt.rps.qry.domain.mappers;

import com.al.qdt.rps.qry.base.AbstractIntegrationTest;
import com.al.qdt.rps.qry.base.EntityTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.al.qdt.common.enums.Hand.ROCK;
import static com.al.qdt.common.helpers.Constants.TEST_ID;
import static com.al.qdt.common.helpers.Constants.TEST_UUID;
import static com.al.qdt.common.helpers.Constants.USERNAME_ONE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Integration testing of the GameProtoMapper interface")
@Tag(value = "mapper")
class GameProtoMapperIT extends AbstractIntegrationTest implements EntityTests {

    @Autowired
    GameProtoMapper gameProtoMapper;

    @Order(1)
    @Test
    @DisplayName("Testing injections")
    void injectionTest() {
        assertNotNull(this.gameProtoMapper);
    }

    @Test
    @DisplayName("Testing of the toDto() method")
    void toDtoTest() {
        final var game = createGame(TEST_UUID, USERNAME_ONE, ROCK);
        final var gameDto = this.gameProtoMapper.toDto(game);

        assertNotNull(gameDto);
        assertEquals(TEST_ID, gameDto.getId());
        assertEquals(game.getId().toString(), gameDto.getId());
        assertEquals(USERNAME_ONE, gameDto.getUsername());
        assertEquals(game.getUsername(), gameDto.getUsername());
        assertEquals(ROCK.name(), gameDto.getHand().name());
        assertEquals(game.getHand().name(), gameDto.getHand().name());
    }
}
