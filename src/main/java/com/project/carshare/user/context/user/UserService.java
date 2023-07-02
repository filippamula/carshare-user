package com.project.carshare.user.context.user;

import com.project.carshare.user.configuration.security.JwtService;
import com.project.carshare.user.context.user.dto.UserInfoResponse;
import com.project.carshare.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserInfoResponse info(){
       var email =  SecurityContextHolder.getContext().getAuthentication().getName();

       var user = userRepository.findByEmail(email)
               .orElseThrow(() -> new RuntimeException("User not found"));

       return UserInfoResponse.builder()
               .firstName(user.getFirstName())
               .lastName(user.getLastName())
               .email(user.getEmail())
               .build();
    }
}
