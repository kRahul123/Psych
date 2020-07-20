package com.psych.game.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.psych.game.Utils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "games")
public class Game extends Auditable{

    @Getter
    @Setter
    @ManyToMany
    @JsonIdentityReference
    private Set<Player> players=new HashSet<>();

    @Getter
    @Setter
    @NotNull
    @ManyToOne
    @JsonIdentityReference
    private GameMode gameMode;


    @OneToMany(mappedBy = "game",cascade = CascadeType.ALL)
    @Getter
    @Setter
    @JsonManagedReference
    private List<Round> rounds=new ArrayList<>();

    @Getter
    @Setter
    @NotNull
    private int numRound=10;

    @Getter
    @Setter
    private boolean hasEllen=false;

    @ManyToOne
    @NotNull
    @Getter
    @Setter
    @JsonIdentityReference
    private Player leader;


    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }

    public int getNumRound() {
        return numRound;
    }

    public void setNumRound(int numRound) {
        this.numRound = numRound;
    }

    public boolean isHasEllen() {
        return hasEllen;
    }

    public void setHasEllen(boolean hasEllen) {
        this.hasEllen = hasEllen;
    }

    public Player getLeader() {
        return leader;
    }

    public void setLeader(Player leader) {
        this.leader = leader;
    }



    public Map<Player, Stats> getPlayerStats() {
        return playerStats;
    }

    public void setPlayerStats(Map<Player, Stats> playerStats) {
        this.playerStats = playerStats;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public Set<Player> getReadyPlayers() {
        return readyPlayers;
    }

    public void setReadyPlayers(Set<Player> readyPlayers) {
        this.readyPlayers = readyPlayers;
    }

    @ManyToMany(cascade = CascadeType.ALL) // cascade defined for stats
    @Getter
    @Setter
    @JsonManagedReference
    private Map<Player,Stats>playerStats=new HashMap<>();

    @Getter
    @Setter
    private GameStatus gameStatus=GameStatus.JOINING;

    @Getter
    @Setter
    @JsonIdentityReference
    @ManyToMany
    private Set<Player>readyPlayers=new HashSet<>();






    public Game(){

    }

    public Game(@NotNull GameMode gameMode,int numRound,Boolean hasEllen,@NotNull Player leader){
        this.gameMode=gameMode;
        this.numRound=numRound;
        this.hasEllen=hasEllen;
        this.leader=leader;
        try {
            addPlayer(leader);
        }catch (Exception ignored){

        }

    }

    public void addPlayer(Player player) throws Exception {
        if(!gameStatus.equals(GameStatus.JOINING)){
            throw new Exception("Cant join !! game already started");
        }
        players.add(player);

    }
    public void removePlayer(Player player) throws Exception {
        if(!players.contains(player))throw new Exception("player does not exist");
        players.remove(player);
        if(player.getCurrentGame().equals(this))player.setCurrentGame(null);
        if(players.size()<=1 && !gameStatus.equals(GameStatus.JOINING))endGame();
    }

    private void endGame() {
        gameStatus=GameStatus.ENDED;
        for (Player player:players){
            if(player.getCurrentGame().equals(this))player.setCurrentGame(null);
            Stats oldPlayerStats=player.getStat();
            Stats currentGameStat=playerStats.get(player);
            oldPlayerStats.setCorrectAnswerCount(oldPlayerStats.getCorrectAnswerCount()+currentGameStat.getCorrectAnswerCount());
            oldPlayerStats.setGotPsychedCount(oldPlayerStats.getGotPsychedCount()+currentGameStat.getGotPsychedCount());
            oldPlayerStats.setPsychedOthersCount(oldPlayerStats.getPsychedOthersCount()+currentGameStat.getPsychedOthersCount());

            //todo update ellen stat

        }
    }

    public void endGame(Player player) throws Exception {
        if(gameStatus.equals(GameStatus.ENDED)){
            throw new Exception("GAme already ended");
        }
        if(!player.equals(leader)){
            throw new Exception("Only leader can end Game");
        }
        endGame();
    }


    public void startGame(Player player) throws Exception {
        if(!gameStatus.equals(GameStatus.JOINING)){
            throw new Exception("Game already started");
        }
        if(players.size()<2){
            throw new Exception("need more player to start game");
        }
        if(!player.equals(leader)){
            throw new Exception("only leader can start game");
        }
        startNewRound();
    }

    private void startNewRound() {
        gameStatus=GameStatus.SUBMITTING_ANSWER;
        Question question= Utils.getRandomQuestion(gameMode);
        Round round=new Round(this,question,rounds.size()+1);
        if(hasEllen){
            round.setEllenAnswer(Utils.getRandomEllenAnswer(question));
        }
        rounds.add(round);
    }

    public void submitAnswer(Player player,String answer) throws Exception {
        if(answer.length()==0){
            throw new Exception("Answer cannot be empty");
        }
        if(!players.contains(player)){
            throw new Exception("Not valid player");
        }
        if(!gameStatus.equals(GameStatus.SUBMITTING_ANSWER)){
            throw new Exception("not accepting answer at present");
        }
        Round currentRound=getCurrentRound();
        currentRound.submitAnswer(player,answer);
        if(currentRound.allAnswerSubmitted(players.size())){
            gameStatus=GameStatus.SELECTING_ANSWER;
        }

    }
    public void selectAnswer(Player player,PlayerAnswer playerAnswer) throws Exception {
        if(!players.contains(player)){
            throw new Exception("player is not in game");

        }
        if(!gameStatus.equals(GameStatus.SELECTING_ANSWER)){
            throw new Exception("game is not accepting answers");
        }
        Round currentRound=getCurrentRound();
        currentRound.selectAnswer(player,playerAnswer);

        if(currentRound.allAnswersSelected(players.size())){
            if(rounds.size()<numRound){
                gameStatus=GameStatus.WAITING_FOR_READY;
            }else{
                endGame();
            }
        }
    }

    public Round getCurrentRound() throws Exception{
        if(rounds.size()==0){
            throw new Exception("game has not started");
        }
        return rounds.get(rounds.size()-1);
    }

    // player ready and not ready

    public void playerIsReady(Player player) throws Exception {
        if(!players.contains(player)){
            throw new Exception("invalid player in game");
        }
        if(!gameStatus.equals(GameStatus.WAITING_FOR_READY)){
            throw new Exception("Game is not waiting for players to be ready");

        }
        readyPlayers.add(player);
        if(readyPlayers.size()==players.size()){
            startNewRound();
        }
    }
    public void playerIsNotReady(Player player)throws Exception{
        if(!players.contains(player)){
            throw new Exception("invalid player in game");
        }
        if(!gameStatus.equals(GameStatus.WAITING_FOR_READY)){
            throw new Exception("Game is not waiting for players to be ready");

        }
        readyPlayers.remove(player);
    }
    public String getSecretCode(){
        return Utils.getSecretCodeFromId(getId());
    }
}
