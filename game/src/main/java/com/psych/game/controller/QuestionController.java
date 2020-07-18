package com.psych.game.controller;

import com.psych.game.model.Player;
import com.psych.game.model.Question;
import com.psych.game.repository.PlayerRepository;
import com.psych.game.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;






        import com.psych.game.model.Player;
        import com.psych.game.repository.PlayerRepository;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.expression.ExpressionException;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;

        import javax.validation.Valid;
        import java.util.List;

@RestController
@RequestMapping("/dev")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/question")
    public List<Question> getAllQuestions(){
        return questionRepository.findAll();
    }

    @GetMapping("/question/{id}")
    public List<Question> getAllQuestions(@PathVariable(value = "id")Long id) throws Exception {
        return (List<Question>) questionRepository.findById(id).orElseThrow(()->new Exception(""));
    }



}
