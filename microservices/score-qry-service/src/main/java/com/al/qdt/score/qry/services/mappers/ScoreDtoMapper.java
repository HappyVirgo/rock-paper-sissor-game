package com.al.qdt.score.qry.services.mappers;

import com.al.qdt.common.mappers.CommonConfig;
import com.al.qdt.score.qry.domain.Score;
import com.al.qdt.common.dto.ScoreDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(config = CommonConfig.class)
public interface ScoreDtoMapper {
    ScoreDto toDto(Score score);
}
