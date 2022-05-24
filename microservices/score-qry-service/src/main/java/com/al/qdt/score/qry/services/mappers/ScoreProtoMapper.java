package com.al.qdt.score.qry.services.mappers;

import com.al.qdt.common.mappers.CommonConfig;
import com.al.qdt.rps.grpc.v1.dto.ScoreDto;
import com.al.qdt.score.qry.domain.Score;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(config = CommonConfig.class)
public interface ScoreProtoMapper {
    ScoreDto toDto(Score score);
}
