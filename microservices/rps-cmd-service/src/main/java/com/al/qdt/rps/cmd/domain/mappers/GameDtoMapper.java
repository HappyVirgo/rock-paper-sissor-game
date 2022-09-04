package com.al.qdt.rps.cmd.domain.mappers;

import com.al.qdt.common.api.dto.GameDto;
import com.al.qdt.common.enums.Hand;
import com.al.qdt.common.infrastructure.mappers.CommonConfig;
import com.al.qdt.rps.cmd.api.commands.PlayGameCommand;
import com.al.qdt.rps.cmd.api.exceptions.InvalidUserInputException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(config = CommonConfig.class)
public interface GameDtoMapper {

    @Mapping(target = "hand", qualifiedByName = "handDtoToHandCommand")
    PlayGameCommand fromDto(GameDto gameDto);

    UUID mapId(String value);

    @Named("handDtoToHandCommand")
    default Hand handDtoToHandCommand(String hand) {
        try {
            return Hand.valueOf(hand);
        } catch (IllegalArgumentException e) {
            throw new InvalidUserInputException();
        }
    }
}
