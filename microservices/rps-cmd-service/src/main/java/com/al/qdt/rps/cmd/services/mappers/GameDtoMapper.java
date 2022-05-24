package com.al.qdt.rps.cmd.services.mappers;

import com.al.qdt.common.dto.GameDto;
import com.al.qdt.common.enums.Hand;
import com.al.qdt.common.mappers.CommonConfig;
import com.al.qdt.rps.cmd.commands.PlayGameCommand;
import com.al.qdt.rps.cmd.exceptions.InvalidUserInputException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = CommonConfig.class)
public interface GameDtoMapper {

    @Mapping(target = "hand", qualifiedByName = "handDtoToHandCommand")
    PlayGameCommand fromDto(GameDto gameDto);

    @Named("handDtoToHandCommand")
    default Hand handDtoToHandCommand(String hand) {
        try {
            return Hand.valueOf(hand);
        } catch (IllegalArgumentException e) {
            throw new InvalidUserInputException();
        }
    }
}
