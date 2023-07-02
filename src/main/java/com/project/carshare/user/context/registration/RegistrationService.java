package com.project.carshare.user.context.registration;

import com.project.carshare.user.context.registration.dto.RegisterRequest;
import com.project.carshare.user.domain.User;
import com.project.carshare.user.domain.UserRepository;
import com.project.carshare.user.domain.enums.Role;
import com.project.carshare.user.domain.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequest request) {

        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("Email already taken");
        }

        var user = User.builder()
                .id(UUID.randomUUID())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .status(Status.ACTIVE)
                .build();

        userRepository.save(user);
    }
}
