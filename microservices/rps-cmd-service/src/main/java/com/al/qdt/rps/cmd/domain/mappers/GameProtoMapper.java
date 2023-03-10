package com.al.qdt.rps.cmd.domain.mappers;

import com.al.qdt.common.infrastructure.mappers.CommonConfig;
import com.al.qdt.common.infrastructure.mappers.ConverterMapper;
import com.al.qdt.rps.cmd.api.commands.PlayGameCommand;
import com.al.qdt.rps.grpc.v1.dto.GameDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(config = CommonConfig.class)
public interface GameProtoMapper extends ConverterMapper {
    @Mapping(target = "hand", expression = "java(enumConverter(gameDto.getHand()))")
    PlayGameCommand fromDto(GameDto gameDto);

    UUID mapId(String value);
}
