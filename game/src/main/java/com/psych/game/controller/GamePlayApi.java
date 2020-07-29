package com.psych.game.controller;


import com.psych.game.Utils;
import com.psych.game.model.*;
import com.psych.game.repository.GameModeRepository;
import com.psych.game.repository.GameRepository;
import com.psych.game.repository.PlayerAnswerRepository;
import com.psych.game.repository.PlayerRepository;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
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

    private static JSONObject success;

    static {
        success=new JSONObject();
        success.put("status","success");
    }

    @ExceptionHandler(Exception.class)
    public JSONObject handleException(Exception e){
        JSONObject error=new JSONObject();
        error.put("status","error");
        error.put("errorText",e.getMessage());
        return error;
    }
    private Player getCurrentPlayer(Authentication authentication){
        return playerRepository.findByEmail(authentication.getName()).orElseThrow();
    }

    @GetMapping("/game-modes")
    public JSONArray gameModes(){
        JSONArray gameModes=new JSONArray();
        for(GameMode gameMode:gameModeRepository.findAll()){
            JSONObject mode=new JSONObject();
            mode.put("title",gameMode.getName());
            mode.put("image",gameMode.getPicture());
            mode.put("description",gameMode.getDescription());
            gameModes.add(mode);
        }
        return gameModes;
    }

    @GetMapping("/player-data")
    public JSONObject playerData(Authentication authentication){
        return playerData(getCurrentPlayer(authentication));
    }

    private JSONObject playerData(Player player){
        JSONObject data=new JSONObject();
        data.put("alias",player.getAlias());
        data.put("picURL",player.getPicURL());
        data.put("psychFaceURL",player.getPsychFaceURL());
        data.put("email",player.getEmail());
        data.put("currentGameId",player.getCurrentGame()==null?null:player.getCurrentGame().getId());

        JSONObject stats=new JSONObject();
        stats.put("correctAnswerCount",player.getStat().getCorrectAnswerCount());
        stats.put("gotPsychedCount",player.getStat().getGotPsychedCount());
        stats.put("psychedOtherCount",player.getStat().getPsychedOthersCount());
        data.put("stats",stats);
        return data;

    }


    @GetMapping("/game-state")
    public JSONObject getGameState(Authentication authentication){
        return gameState(getCurrentPlayer(authentication).getCurrentGame());
    }
    private JSONObject gameState(Game game){
        JSONObject data=new JSONObject();
        if(game==null) return data;
        data.put("id",game.getId());
        data.put("gameMode",game.getGameMode());
        data.put("gameStatus",game.getGameStatus());
        data.put("hasEllen",game.isHasEllen());
        data.put("secret_code",game.getSecretCode());
        data.put("numRound",game.getNumRound());

        JSONObject roundData=new JSONObject();
        try {
            Round lastRound=game.getCurrentRound();
            roundData.put("id",lastRound.getId());
            roundData.put("question",lastRound.getQuestion());
            roundData.put("round number",lastRound.getRoundNumber());

        }catch (Exception ignored){

        }
        data.put("round",roundData);
        return data;





    }




    @GetMapping("/leaderboard")
    public JSONArray leaderboard(){
        JSONArray data=new JSONArray();
        for(Player player:playerRepository.findAll()){
            JSONObject stat=new JSONObject();
            stat.put("alias",player.getAlias());
            stat.put("picURL",player.getPicURL());
            stat.put("correctAnswerCount",player.getStat().getCorrectAnswerCount());
            stat.put("gotPsychedCount",player.getStat().getGotPsychedCount());
            stat.put("psychedOtherCount",player.getStat().getPsychedOthersCount());
            data.add(stat);
        }
        return data;
    }






    public JSONObject updateProfile(
            Authentication authentication,
            @RequestParam(name = "alias")String alias,
            @RequestParam(name = "email")String email,
            @RequestParam(name = "psychFaceURL")String psychFaceURL,
            @RequestParam(name = "picURL")String picURL

    ){
        Player player=getCurrentPlayer(authentication);
        player.setAlias(alias);
        player.setEmail(email);
        player.setPsychFaceURL(psychFaceURL);
        player.setPicURL(picURL);
        playerRepository.save(player);
        return  success;
    }


    @GetMapping("/create-game")
    public JSONObject createGame(Authentication authentication,
        @RequestParam(name = "gameMode")String gameMode,
                                 @RequestParam(name = "numRound")int numRound,
                                 @RequestParam(name = "hasEllen")boolean hasEllen
    ){
        Player leader=getCurrentPlayer(authentication);
        GameMode mode=gameModeRepository.findByName(gameMode).orElseThrow();
        gameRepository.save(new Game(mode,numRound,hasEllen,leader));
        return success;
    }


    @GetMapping("/join-game")
    public JSONObject joinGame(Authentication authentication,
                               @RequestParam(name = "gameCode")String gameCode) throws Exception {
        Player player=getCurrentPlayer(authentication);
        Optional<Game> game=gameRepository.findById(Utils.getGameIdFromSecretCode(gameCode));
        if(game.isEmpty()){
            throw new Exception("invalid secret code");
        }
        game.get().addPlayer(player);
        return success;
    }


    @GetMapping("leave-game")
    public JSONObject leaveGame(Authentication authentication) throws Exception {
        Player player=getCurrentPlayer(authentication);
        player.getCurrentGame().removePlayer(player);
        return success;
    }




    @GetMapping("/start-game")
    public JSONObject startGame(Authentication authentication) throws Exception {
        Player leader=getCurrentPlayer(authentication);
        leader.getCurrentGame().startGame(leader);
        return success;
    }



    @GetMapping("/end-game")
    public JSONObject endGame(Authentication authentication) throws Exception {
        Player leader=getCurrentPlayer(authentication);
        leader.getCurrentGame().endGame(leader);
        return success;
    }



    @GetMapping("/submit-answer")
    public JSONObject submitAnswer(Authentication authentication,
                                   @RequestParam(name = "answer")String answer
                                   ) throws Exception {
        Player player=getCurrentPlayer(authentication);
        Game game=player.getCurrentGame();
        game.submitAnswer(player,answer);
        return success;
    }



    @GetMapping("/select-answer")
    public JSONObject selectAnswer(Authentication authentication,
                                   @RequestParam(name = "answerId")long answerId) throws Exception {
        Player player=getCurrentPlayer(authentication);
        Game game=player.getCurrentGame();
        PlayerAnswer playerAnswer=playerAnswerRepository.findById(answerId).orElseThrow();
        game.selectAnswer(player,playerAnswer);
        return success;
    }



    @GetMapping("/player-ready")
    public JSONObject playerReady(Authentication authentication) throws Exception {
        Player player=getCurrentPlayer(authentication);
        Game game=player.getCurrentGame();
        game.playerIsReady(player);
        return  success;
    }

    @GetMapping("/player-not-ready")
    public JSONObject playerNotReady(Authentication authentication) throws Exception {
        Player player=getCurrentPlayer(authentication);
        Game game=player.getCurrentGame();
        game.playerIsNotReady(player);
        return success;
    }



}
