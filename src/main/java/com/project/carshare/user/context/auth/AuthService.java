package com.project.carshare.user.context.auth;

import com.project.carshare.user.configuration.security.JwtService;
import com.project.carshare.user.context.auth.dto.AuthorizationResponse;
import com.project.carshare.user.context.auth.dto.LoginRequest;
import com.project.carshare.user.context.auth.dto.RegisterRequest;
import com.project.carshare.user.domain.User;
import com.project.carshare.user.domain.UserRepository;
import com.project.carshare.user.domain.enums.Role;
import com.project.carshare.user.domain.enums.UserStatus;
import com.project.carshare.user.domain.enums.VerificationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
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

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already taken");
        }

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .status(UserStatus.ACTIVE)
                .verificationStatus(VerificationStatus.NOT_VERIFIED)
                .drivingLicense(null)
                .pesel(null)
                .dateOfBirth(null)
                .phoneNo(null)
                .build();

        userRepository.save(user);
    }

    public AuthorizationResponse login(LoginRequest request) {
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.isArchived()) {
            throw new RuntimeException("User not found");
        }
        if (user.isLocked()) {
            throw new RuntimeException("User is locked");
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        var jwtToken = jwtService.generateToken(user);
        user.setLastToken(jwtToken);
        userRepository.save(user);
        return AuthorizationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    public void logout() {
        var userId = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setLastToken(null);
        userRepository.save(user);
    }
}
