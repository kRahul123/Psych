package com.psych.game.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name="rounds")
public class Round extends Auditable{
    @ManyToOne
    @Getter
    @Setter
    @NotNull
    private Game game;

    @ManyToOne
    @Getter
    @NotNull
    @Setter
    private Question question;

    @Getter
    @Setter
    private  int roundNumber;

    @ManyToMany
    private Map<Player,PlayerAnswer>submittedAnswers=new HashMap<>();

    @ManyToMany
    private Map<Player,PlayerAnswer>selectedAnswer=new HashMap<>();

    @ManyToOne
    private EllenAnswer ellenAnswer;

    public Game getGame() {
        return game;
    }

    public Map<Player, PlayerAnswer> getSubmittedAnswers() {
        return submittedAnswers;
    }

    public void setSubmittedAnswers(Map<Player, PlayerAnswer> submittedAnswers) {
        this.submittedAnswers = submittedAnswers;
    }

    public Map<Player, PlayerAnswer> getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(Map<Player, PlayerAnswer> selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    public EllenAnswer getEllenAnswer() {
        return ellenAnswer;
    }

    public void setEllenAnswer(EllenAnswer ellenAnswer) {
        this.ellenAnswer = ellenAnswer;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }


    public Round(){

    }
    public Round(Game game,Question question,int roundNumber){
        this.game=game;
        this.roundNumber=roundNumber;
        this.question=question;
    }

    public void submitAnswer(Player player, String answer) throws Exception{
        if (submittedAnswers.containsKey(player)){
            throw new Exception("player has already submitted the answer");
        }
        for(PlayerAnswer existingAnswer:submittedAnswers.values()){
            if(answer.equals(existingAnswer)){
                throw new Exception("answer already exist");
            }
        }
        submittedAnswers.put(player,new PlayerAnswer(this,player,answer));
    }
    public boolean allAnswerSubmitted(int numPlayer){
        return submittedAnswers.size()==numPlayer;
    }

    public boolean allAnswersSelected(int numPlayer) {
        return selectedAnswer.size()==numPlayer;
    }

    public void selectAnswer(Player player, PlayerAnswer playerAnswer) throws Exception {
        if(selectedAnswer.containsKey(player)){
            throw new Exception("already selected answer");
        }
        if(playerAnswer.getPlayer().equals(player)){
            throw new Exception("cant select own answer");
        }
        if(!playerAnswer.getRound().equals(this)){
            throw new Exception("no such answer submitted in this round");
        }
        selectedAnswer.put(player,playerAnswer);
    }
}
