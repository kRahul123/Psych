package com.psych.game.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

@Entity
@Table(name = "stats")
public class Stats extends Auditable{
    @Getter
    @Setter
    private long correctAnswerCount=0;
    @Getter
    @Setter
    private long gotPsychedCount=0;
    @Getter
    @Setter
    private long psychedOthersCount=0;

    public long getCorrectAnswerCount() {
        return correctAnswerCount;
    }

    public void setCorrectAnswerCount(long correctAnswerCount) {
        this.correctAnswerCount = correctAnswerCount;
    }

    public long getGotPsychedCount() {
        return gotPsychedCount;
    }

    public void setGotPsychedCount(long gotPsychedCount) {
        this.gotPsychedCount = gotPsychedCount;
    }

    public long getPsychedOthersCount() {
        return psychedOthersCount;
    }

    public void setPsychedOthersCount(long psychedOthersCount) {
        this.psychedOthersCount = psychedOthersCount;
    }
}
