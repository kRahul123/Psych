package com.psych.game.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User extends Auditable{

    @Email
    @NotBlank
    @Column(unique = true)
    @Getter
    @Setter
    private String email;

    @Getter
    @NotBlank
    private String saltedHashPassword;

    public void setSaltedHashPassword(String value){
        this.saltedHashPassword=new BCryptPasswordEncoder(5).encode(value);

    }

    @ManyToMany(fetch = FetchType.EAGER)
    @Getter
    @Setter
    private Set<Role> roles=new HashSet<>();

    public String getEmail() {
        return email;
    }

    public String getSaltedHashPassword() {
        return saltedHashPassword;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public User(){

    }
    public User(User user){
        email=user.getEmail();
        saltedHashPassword=user.getSaltedHashPassword();
        roles=user.getRoles();
    }
}
