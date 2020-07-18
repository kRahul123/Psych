package com.psych.game.config;

import org.springframework.context.annotation.Bean;

public class SpringConfiguration {
    @Bean
    public static ApplicationContextProvider contextProvider(){
        return new ApplicationContextProvider();
    }
}
