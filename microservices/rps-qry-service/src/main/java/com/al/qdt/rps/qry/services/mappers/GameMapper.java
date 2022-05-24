package com.al.qdt.rps.qry.services.mappers;

import com.al.qdt.common.events.rps.GamePlayedEvent;
import com.al.qdt.common.mappers.CommonConfig;
import com.al.qdt.rps.qry.domain.Game;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Component
@Mapper(config = CommonConfig.class,
        builder = @Builder(disableBuilder = true))
public abstract class GameMapper {

    /**
     * Converts game played event {@link GamePlayedEvent} to game entity class {@link Game}.
     *
     * @param gamePlayedEvent game played event
     * @return game entity
     */
    public abstract Game toEntity(GamePlayedEvent gamePlayedEvent);

    @BeforeMapping
    protected void mapBaseEntityId(GamePlayedEvent gamePlayedEvent, @MappingTarget Game entity) {
        entity.setId(gamePlayedEvent.getId());
    }
}
