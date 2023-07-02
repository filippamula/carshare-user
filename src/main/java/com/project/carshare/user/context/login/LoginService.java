package com.project.carshare.user.context.login;

import com.project.carshare.user.configuration.security.JwtService;
import com.project.carshare.user.context.login.dto.AuthorizationResponse;
import com.project.carshare.user.context.login.dto.LoginRequest;
import com.project.carshare.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    public AuthorizationResponse login(LoginRequest request) {

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid username or password");
        }

        var jwtToken = jwtService.generateToken(user);
        return AuthorizationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }
}
