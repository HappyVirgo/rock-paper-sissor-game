package com.al.qdt.score.qry.services.mappers;

import com.al.qdt.common.dto.ScoreDto;
import com.al.qdt.common.mappers.CommonConfig;
import com.al.qdt.score.qry.domain.Score;
import org.mapstruct.Mapper;

@Mapper(config = CommonConfig.class)
public interface ScoreDtoMapper {
    ScoreDto toDto(Score score);
}
