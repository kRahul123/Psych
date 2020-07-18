package com.psych.game.auth;

import com.psych.game.model.User;
import com.psych.game.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty())
            try {
                throw new Exception("No user registered with " + email);
            } catch (Exception e) {
                e.printStackTrace();
            }
        return new CustomUserDetails(user.get());
    }
}
