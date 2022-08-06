package com.al.qdt.score.qry.services.mappers;

import com.al.qdt.common.dto.ScoreDto;
import com.al.qdt.common.mappers.CommonConfig;
import com.al.qdt.score.qry.domain.Score;
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
