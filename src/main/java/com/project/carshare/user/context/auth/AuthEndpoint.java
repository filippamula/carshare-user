package com.project.carshare.user.context.auth;

import com.project.carshare.user.context.auth.dto.RegisterRequest;
import com.project.carshare.user.context.auth.dto.AuthorizationResponse;
import com.project.carshare.user.context.auth.dto.LoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthEndpoint {

    private final AuthService authService;

    @Operation(description = "User registration")
    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @RequestBody RegisterRequest request) {

        authService.register(request);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "User login")
    @PostMapping("/login")
    public ResponseEntity<AuthorizationResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @Operation(description = "User logout")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        authService.logout();
        return ResponseEntity.ok().build();
    }

    @Operation(description = "Internal endpoint used for verification between microservices")
    @PostMapping("/internal/verify")
    public ResponseEntity<Void> validateToken() {
            return ResponseEntity.ok().build();
    }
}
