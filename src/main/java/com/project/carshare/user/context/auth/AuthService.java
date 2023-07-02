package com.project.carshare.user.context.auth;

import com.project.carshare.user.configuration.security.JwtService;
import com.project.carshare.user.context.auth.dto.AuthorizationResponse;
import com.project.carshare.user.context.auth.dto.LoginRequest;
import com.project.carshare.user.context.auth.dto.RegisterRequest;
import com.project.carshare.user.domain.User;
import com.project.carshare.user.domain.UserRepository;
import com.project.carshare.user.domain.enums.Role;
import com.project.carshare.user.domain.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

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

    public AuthorizationResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        return AuthorizationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }
}
