package com.psych.game.model;

import org.hibernate.validator.constraints.URL;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "game_mode")
public class GameMode extends Auditable{

    @NotBlank
    private String name;
    private String picture;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public GameMode(){

    }

    public GameMode(@NotBlank String name, @URL String picture,String description){
        this.name=name;
        this.picture=picture;
        this.description=description;
    }
}
