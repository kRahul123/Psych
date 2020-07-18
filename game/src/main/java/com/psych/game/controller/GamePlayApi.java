package com.psych.game.controller;


import com.psych.game.repository.GameModeRepository;
import com.psych.game.repository.GameRepository;
import com.psych.game.repository.PlayerAnswerRepository;
import com.psych.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/play")
public class GamePlayApi {
    @Autowired
    private GameModeRepository gameModeRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private PlayerAnswerRepository playerAnswerRepository;





}
