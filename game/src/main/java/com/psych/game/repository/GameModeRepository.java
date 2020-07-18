package com.psych.game.repository;

import com.psych.game.model.GameMode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameModeRepository extends JpaRepository<GameMode,Long> {
    Optional<GameMode> findByName(String gameMode);
}
