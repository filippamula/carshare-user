package com.project.carshare.user.context.auth;

import com.project.carshare.user.context.auth.dto.RegisterRequest;
import com.project.carshare.user.context.auth.dto.AuthorizationResponse;
import com.project.carshare.user.context.auth.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthEndpoint {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @RequestBody RegisterRequest request) {

        authService.register(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthorizationResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        authService.logout();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/internal/verify")
    public ResponseEntity<Void> validateToken() {
            return ResponseEntity.ok().build();
    }
}
