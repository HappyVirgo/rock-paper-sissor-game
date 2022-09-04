package com.al.qdt.score.qry.domain.mappers;

import com.al.qdt.common.api.dto.ScoreDto;
import com.al.qdt.common.infrastructure.mappers.CommonConfig;
import com.al.qdt.score.qry.domain.entities.Score;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CommonConfig.class)
public interface ScoreDtoMapper {

    /**
     * Converts score entity object {@link Score} to score dto object {@link ScoreDto}.
     *
     * @param score score entity
     * @return score data transfer object
     */
    @Mapping(target = "id", expression = "java(score.getId().toString())")
    ScoreDto toDto(Score score);
}
