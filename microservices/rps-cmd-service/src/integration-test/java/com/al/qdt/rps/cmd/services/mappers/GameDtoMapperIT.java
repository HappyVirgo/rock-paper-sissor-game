package com.al.qdt.rps.cmd.services.mappers;

import com.al.qdt.rps.cmd.base.AbstractIntegrationTest;
import com.al.qdt.rps.cmd.base.DtoTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.al.qdt.common.enums.Hand.ROCK;
import static com.al.qdt.common.helpers.Constants.USERNAME_ONE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Integration testing of the GameDtoMapper interface")
@Tag(value = "mapper")
class GameDtoMapperIT extends AbstractIntegrationTest implements DtoTests {

    @Autowired
    GameDtoMapper gameDtoMapper;

    @Order(1)
    @Test
    @DisplayName("Testing injections")
    void injectionTest() {
        assertNotNull(this.gameDtoMapper);
    }

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
