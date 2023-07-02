package com.project.carshare.user.context.user;

import com.project.carshare.user.context.user.dto.ChangePasswordRequest;
import com.project.carshare.user.context.user.dto.UserInfoResponse;
import com.project.carshare.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserInfoResponse info(){
       var email =  SecurityContextHolder.getContext().getAuthentication().getName();

       var user = userRepository.findByEmail(email)
               .orElseThrow(() -> new RuntimeException("User not found"));

       return UserInfoResponse.builder()
               .id(user.getId())
               .firstName(user.getFirstName())
               .lastName(user.getLastName())
               .email(user.getEmail())
               .build();
    }

    public void changePassword(ChangePasswordRequest request) {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();

        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())){
            throw new RuntimeException("Incorrect password");
        }
        if(!request.getNewPassword().equals(request.getConfirmNewPassword())){
            throw new RuntimeException("New passwords don't match");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}
