package com.psych.game.repository;

import com.psych.game.model.EllenAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EllenAnswerRepository extends JpaRepository<EllenAnswer,Long> {

    @Query(value = "select * from ellen_answers where question_id =:questionId order by random() limit 1",nativeQuery = true)
    EllenAnswer getRandomAnswer(Long questionId);
}
