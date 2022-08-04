package com.al.qdt.score.qry.services;

import com.al.qdt.cqrs.infrastructure.QueryDispatcher;
import com.al.qdt.rps.grpc.v1.common.Player;
import com.al.qdt.rps.grpc.v1.dto.ScoreDto;
import com.al.qdt.rps.grpc.v1.services.ListOfScoresResponse;
import com.al.qdt.score.qry.domain.Score;
import com.al.qdt.score.qry.queries.FindAllScoresQuery;
import com.al.qdt.score.qry.queries.FindScoreByIdQuery;
import com.al.qdt.score.qry.queries.FindScoresByWinnerQuery;
import com.al.qdt.score.qry.services.mappers.ScoreProtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.al.qdt.score.qry.config.CacheConfig.SCORES_PROTO_CACHE_NAME;
import static com.al.qdt.score.qry.config.CacheConfig.SCORE_CACHE_NAMES;
import static com.al.qdt.score.qry.config.CacheConfig.SCORE_PROTO_CACHE_NAME;
import static com.al.qdt.score.qry.config.CacheConfig.WINNERS_PROTO_CACHE_NAME;

@Slf4j
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = SCORE_CACHE_NAMES)
public class ScoreServiceV2Impl implements ScoreServiceV2 {
    private static final int IDX = 0;

    private final QueryDispatcher queryDispatcher;
    private final ScoreProtoMapper scoreProtoMapper;

    @Override
    @Cacheable(cacheNames = SCORES_PROTO_CACHE_NAME, sync = true)
    public ListOfScoresResponse all() {
        log.info("SERVICE: Getting all scores.");
        return this.toListOfScoreDto(this.queryDispatcher.send(new FindAllScoresQuery()));
    }

    @Override
    @Cacheable(cacheNames = SCORE_PROTO_CACHE_NAME, key = "#id.toString()", sync = true)
    public ScoreDto findById(UUID id) {
        log.info("SERVICE: Finding scores by id: {}.", id.toString());
        final List<Score> scores = this.queryDispatcher.send(new FindScoreByIdQuery(id));
        return this.scoreProtoMapper.toDto(scores.get(IDX));
    }

    @Override
    @Cacheable(cacheNames = WINNERS_PROTO_CACHE_NAME, key = "#winner.name()", sync = true)
    public ListOfScoresResponse findByWinner(Player player) {
        final var winner = player.name();
        log.info("SERVICE: Finding scores by winner: {}.", winner);
        return this.toListOfScoreDto(this.queryDispatcher.send(new FindScoresByWinnerQuery(winner)));
    }

    /**
     * Converts score entities to dto objects.
     *
     * @param scores scores
     * @return collection of score dto objects
     */
    private ListOfScoresResponse toListOfScoreDto(Iterable<Score> scores) {
        final List<ScoreDto> scoreDtoList = new ArrayList<>();
        scores.forEach(score -> scoreDtoList.add(this.scoreProtoMapper.toDto(score)));
        return ListOfScoresResponse.newBuilder()
                .addAllScores(scoreDtoList)
                .build();
    }
}
