package com.al.qdt.rps.cmd.domain.mappers;

import com.al.qdt.rps.cmd.base.ProtoTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static com.al.qdt.common.helpers.Constants.USERNAME_ONE;
import static com.al.qdt.rps.grpc.v1.common.Hand.ROCK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Testing of the GameProtoMapper interface")
@Tag(value = "mapper")
class GameProtoMapperTest implements ProtoTests {
    final GameProtoMapper gameProtoMapper = Mappers.getMapper(GameProtoMapper.class);

    @Test
    @DisplayName("Testing of the fromDto() method")
    void fromDtoTest() {
        final var gameDto = createGameDto();
        final var playGameCommand = this.gameProtoMapper.fromDto(gameDto);

        assertNotNull(playGameCommand);
        assertEquals(USERNAME_ONE, playGameCommand.getUsername());
        assertEquals(gameDto.getUsername(), playGameCommand.getUsername());
        assertEquals(ROCK.getValueDescriptor().getName(), playGameCommand.getHand().name());
        assertEquals(gameDto.getHand().getValueDescriptor().getName(), playGameCommand.getHand().name());
    }
}
