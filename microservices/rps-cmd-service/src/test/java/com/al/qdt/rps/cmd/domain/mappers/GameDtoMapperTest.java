package com.al.qdt.rps.cmd.domain.mappers;

import com.al.qdt.rps.cmd.base.DtoTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static com.al.qdt.common.enums.Hand.ROCK;
import static com.al.qdt.common.helpers.Constants.USERNAME_ONE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Testing of the GameDtoMapper interface")
@Tag(value = "mapper")
class GameDtoMapperTest implements DtoTests {
    final GameDtoMapper gameDtoMapper = Mappers.getMapper(GameDtoMapper.class);

    @Test
    @DisplayName("Testing of the fromDto() method with valid parameters")
    void fromDtoTest() {
        final var gameDto = createGameDto(USERNAME_ONE);
        final var playGameCommand = this.gameDtoMapper.fromDto(gameDto);

        assertNotNull(playGameCommand);
        assertEquals(USERNAME_ONE, playGameCommand.getUsername());
        assertEquals(gameDto.getUsername(), playGameCommand.getUsername());
        assertEquals(ROCK, playGameCommand.getHand());
        assertEquals(gameDto.getHand(), playGameCommand.getHand().name());
    }
}
