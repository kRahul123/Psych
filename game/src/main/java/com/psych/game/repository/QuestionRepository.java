package com.psych.game.repository;

import com.psych.game.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends JpaRepository<Question,Long> {
    @Query(value = "select * from questions where game_mode_id=:gameModeId order by random() limit 1",nativeQuery = true)
    Question getRandomQuestion(Long gameModeId);
}
