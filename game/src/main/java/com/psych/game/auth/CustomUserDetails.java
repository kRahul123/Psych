package com.psych.game.auth;

import com.psych.game.model.Role;
import com.psych.game.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class CustomUserDetails extends User implements UserDetails {
    public CustomUserDetails(User user){
        super(user);
    }
    public Collection<? extends GrantedAuthority>getAuthorities(){
        Set<Role>roles=super.getRoles();
        Collection<GrantedAuthority> authorities=new ArrayList<>();
        for(Role role:roles){
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));

        }
        return authorities;
    }
    @Override
    public String getPassword() {
        return super.getSaltedHashPassword();
    }

    @Override
    public String getUsername() {
        return super.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
