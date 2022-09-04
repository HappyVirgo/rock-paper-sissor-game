package com.al.qdt.rps.qry.domain.mappers;

import com.al.qdt.common.infrastructure.mappers.CommonConfig;
import com.al.qdt.rps.grpc.v1.dto.GameDto;
import com.al.qdt.rps.qry.domain.entities.Game;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CommonConfig.class)
public interface GameProtoMapper {

    /**
     * Converts game entity object {@link Game} to game dto object {@link GameDto}.
     *
     * @param game game entity
     * @return game Data transfer object
     */
    @Mapping(target = "id", expression = "java(game.getId().toString())")
    GameDto toDto(Game game);
}
