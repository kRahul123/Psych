package com.psych.game.model;


import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.internal.metadata.aggregated.rule.OverridingMethodMustNotAlterParameterConstraints;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name="players")
public class Player extends User {
    @NotBlank
    @Getter
    @Setter
    private String alias;

    @Getter
    @Setter
    @URL
    private String psychFaceURL;

    @Setter
    @URL
    @Getter
    private String picURL;

    @OneToOne(cascade = CascadeType.ALL)
    @Getter
    @Setter
    private Stats stat=new Stats();

    @ManyToMany(mappedBy = "players")
    @Getter
    @Setter
    @JsonIdentityReference
    private Set<Game> games=new HashSet<>();

    @ManyToOne
    @Getter
    @Setter
    private Game currentGame=null;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }



    public String getPsychFaceURL() {
        return psychFaceURL;
    }

    public void setPsychFaceURL(String psychFaceURL) {
        this.psychFaceURL = psychFaceURL;
    }

    public String getPicURL() {
        return picURL;
    }

    public void setPicURL(String picURL) {
        this.picURL = picURL;
    }

    public Stats getStat() {
        return stat;
    }

    public void setStat(Stats stat) {
        this.stat = stat;
    }

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    public Player(){

    }

    private Player(Builder builder){
        setEmail(builder.email);
        setSaltedHashPassword(builder.saltedHashPassword);
        setAlias(builder.alias);
        setPsychFaceURL(builder.psychFaceURL);
        setPicURL(builder.picURL);
    }
    public static final class Builder{
        @Email
        @NotBlank
        private String email;

        @NotBlank
        private String saltedHashPassword;
        @NotBlank
        private String alias;
        private String psychFaceURL;
        private String picURL;

        public Builder(){

        }
        public Builder email(@Email @NotBlank String val){
            email= val;
            return this;
        }
        public Builder saltedHashPassword(@NotBlank String val){
            saltedHashPassword=val;
            return this;
        }
        public Builder alias(@NotBlank String val){
            alias=val;
            return this;
        }
        public Builder psychFaceURL(String val){
            psychFaceURL=val;
            return this;
        }
        public Builder picURL(String val){
            picURL=val;
            return this;

        }
        public Player build(){
            return new Player(this);
        }
    }

}
