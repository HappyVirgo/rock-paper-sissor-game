package com.al.qdt.score.qry.domain.repositories;

import com.al.qdt.common.enums.Player;
import com.al.qdt.score.qry.domain.entities.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ScoreRepository extends JpaRepository<Score, UUID> {
    List<Score> findByWinner(Player winner);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "DELETE FROM score s WHERE s.id = :id",
            nativeQuery = true)
    void deleteById(@Param("id") UUID id);
}
