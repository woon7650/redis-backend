package com.example.login.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry
                .addMapping("/**")
                .allowedOrigins("http://localhost:8080","http://localhost:8081","http://localhost:8082","http://localhost:8083")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowCredentials(true);
    }

}
