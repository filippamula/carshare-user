package com.project.carshare.user.configuration;

import com.project.carshare.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {

    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService(){
        return username ->  userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User: " + username + " not found"));
    }
}
