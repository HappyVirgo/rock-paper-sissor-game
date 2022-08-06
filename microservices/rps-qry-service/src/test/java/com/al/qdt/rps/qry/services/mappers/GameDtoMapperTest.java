package com.al.qdt.rps.qry.services.mappers;

import com.al.qdt.rps.qry.base.EntityTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static com.al.qdt.common.enums.Hand.ROCK;
import static com.al.qdt.common.helpers.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Testing of the GameDtoMapper interface")
@Tag(value = "mapper")
class GameDtoMapperTest implements EntityTests {
    final GameDtoMapper gameDtoMapper = Mappers.getMapper(GameDtoMapper.class);

    @Test
    @DisplayName("Testing of the toDto() method")
    void toDtoTest() {
        final var game = createGame(TEST_UUID, USERNAME_ONE, ROCK);
        final var gameDto = this.gameDtoMapper.toDto(game);

        assertNotNull(gameDto);
        assertEquals(TEST_ID, gameDto.getId());
        assertEquals(game.getId().toString(), gameDto.getId());
        assertEquals(USERNAME_ONE, gameDto.getUsername());
        assertEquals(game.getUsername(), gameDto.getUsername());
        assertEquals(ROCK.name(), gameDto.getHand());
        assertEquals(game.getHand().name(), gameDto.getHand());
    }
}
