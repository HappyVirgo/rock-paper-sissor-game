package com.al.qdt.rps.qry.repositories;

import com.al.qdt.rps.qry.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID> {
    List<Game> findGameByUsername(String username);
}
