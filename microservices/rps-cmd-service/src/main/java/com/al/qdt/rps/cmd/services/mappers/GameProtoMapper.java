package com.al.qdt.rps.cmd.services.mappers;

import com.al.qdt.rps.cmd.commands.PlayGameCommand;
import com.al.qdt.common.mappers.CommonConfig;
import com.al.qdt.common.mappers.ConverterMapper;
import com.al.qdt.rps.grpc.v1.dto.GameDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(config = CommonConfig.class)
public interface GameProtoMapper extends ConverterMapper {
    @Mapping(target = "hand", expression = "java(enumConverter(gameDto.getHand()))")
    PlayGameCommand fromDto(GameDto gameDto);
}
