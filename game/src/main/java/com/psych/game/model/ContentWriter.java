package com.psych.game.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "content_writers")
public class ContentWriter extends Employee {

    public Set<Question> getEditedQuestions() {
        return editedQuestions;
    }

    public void setEditedQuestions(Set<Question> editedQuestions) {
        this.editedQuestions = editedQuestions;
    }

    @Getter
    @Setter
    @ManyToMany
    Set<Question> editedQuestions=new HashSet<>();

}
