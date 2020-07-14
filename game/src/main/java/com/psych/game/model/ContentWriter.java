package com.psych.game.model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "content_writers")
public class ContentWriter extends Employee {

    @ManyToMany
    List<Question> editedQuestions;

}
