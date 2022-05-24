package com.al.qdt.score.qry.services.mappers;

import com.al.qdt.common.events.score.ScoresAddedEvent;
import com.al.qdt.common.mappers.CommonConfig;
import com.al.qdt.score.qry.domain.Score;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Component
@Mapper(config = CommonConfig.class,
        builder = @Builder(disableBuilder = true))
public abstract class ScoreMapper {

    /**
     * Converts scores added event {@link ScoresAddedEvent} to score entity class {@link Score}.
     *
     * @param scoresAddedEvent scores added event
     * @return score entity
     */
    public abstract Score toEntity(ScoresAddedEvent scoresAddedEvent);

    @BeforeMapping
    protected void mapBaseEntityId(ScoresAddedEvent scoresAddedEvent, @MappingTarget Score entity) {
        entity.setId(scoresAddedEvent.getId());
    }
}
