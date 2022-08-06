package com.al.qdt.score.qry.repositories;

import com.al.qdt.common.enums.Player;
import com.al.qdt.score.qry.domain.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ScoreRepository extends JpaRepository<Score, UUID> {
    List<Score> findByWinner(Player winner);

    @Modifying
    @Query(value = "DELETE FROM score s WHERE s.id = ?1",
            nativeQuery = true)
    void deleteById(UUID id);
}
