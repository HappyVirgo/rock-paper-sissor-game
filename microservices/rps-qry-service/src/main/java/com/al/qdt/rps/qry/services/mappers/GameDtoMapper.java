package com.al.qdt.rps.qry.services.mappers;

import com.al.qdt.common.dto.GameDto;
import com.al.qdt.common.mappers.CommonConfig;
import com.al.qdt.rps.qry.domain.Game;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(config = CommonConfig.class)
public interface GameDtoMapper {

    /**
     * Converts game entity object {@link Game} to game dto object {@link GameDto}.
     *
     * @param game game played event
     * @return game entity
     */
    GameDto toDto(Game game);
}
