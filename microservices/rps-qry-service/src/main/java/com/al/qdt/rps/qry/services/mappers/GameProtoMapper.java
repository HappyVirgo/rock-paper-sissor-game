package com.al.qdt.rps.qry.services.mappers;

import com.al.qdt.common.mappers.CommonConfig;
import com.al.qdt.rps.grpc.v1.dto.GameDto;
import com.al.qdt.rps.qry.domain.Game;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(config = CommonConfig.class)
public interface GameProtoMapper {

    GameDto toDto(Game game);
}
