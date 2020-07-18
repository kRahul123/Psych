package com.psych.game.controller;

import com.psych.game.Constants;
import com.psych.game.Pair;
import com.psych.game.Utils;
import com.psych.game.model.GameMode;
import com.psych.game.model.Player;
import com.psych.game.model.Question;
import com.psych.game.repository.PlayerRepository;
import com.psych.game.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dev")
public class PopulateDb {
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/add-questions-from-file")
    public void addQuestions() throws IOException {
        questionRepository.deleteAll();
        for(Map.Entry<String,GameMode>entry: Constants.qa_files.entrySet()) {
            int questionNumber=0;
            for (Pair<String, String> question_answer : Utils.readQAFile(entry.getKey())) {
                Question q = new Question();
                q.setQuestionText(question_answer.getFirst());
                q.setCorrectAnswer(question_answer.getSecond());
                q.setGameMode(entry.getValue());
                questionRepository.save(q);
                questionNumber++;
                if(questionNumber>Constants.MAX_QUESTIONS_TO_READ){
                    break;
                }
            }
        }

    }

    @GetMapping("/add-dummy-players")
    public void addPlayers(){
        playerRepository.deleteAll();
        Player luffy =new Player();
        luffy.setName("Monkey D. Luffy");
        Player ace =new Player();
        luffy.setName("Portogus D. Ace");
        playerRepository.save(luffy);
        playerRepository.save(ace);
    }



}
