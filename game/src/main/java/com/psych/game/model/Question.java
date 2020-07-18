package com.psych.game.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.psych.game.Constants;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "questions")
public class Question extends Auditable{
    @Getter
    @Setter
    @NotBlank
    @Column(length = Constants.MAX_QUESTION_LENGTH)
    private String questionText;

    @Getter
    @Setter
    @NotBlank
    @Column(length = Constants.MAX_ANSWER_LENGTH)
    private String correctAnswer;

    @ManyToOne
    @NotNull
    @Getter
    @Setter
    @JsonIdentityReference
    private GameMode gameMode;

    @OneToMany(mappedBy = "question",cascade = CascadeType.ALL)
    @Getter
    @Setter
    @JsonManagedReference
    private Set<EllenAnswer> ellenAnswers=new HashSet<>();

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public Set<EllenAnswer> getEllenAnswers() {
        return ellenAnswers;
    }

    public void setEllenAnswers(Set<EllenAnswer> ellenAnswers) {
        this.ellenAnswers = ellenAnswers;
    }
    public Question(@NotNull String questionText,@NotNull String correctAnswer,@NotNull GameMode gameMode){
        this.questionText=questionText;
        this.correctAnswer=correctAnswer;
        this.gameMode=gameMode;
    }
    public Question(){

    }
}
