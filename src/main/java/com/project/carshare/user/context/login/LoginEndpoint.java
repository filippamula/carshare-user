package com.project.carshare.user.context.login;

import com.project.carshare.user.context.login.dto.AuthorizationResponse;
import com.project.carshare.user.context.login.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user/login")
@RequiredArgsConstructor
public class LoginEndpoint {

    private final LoginService loginService;

    @PostMapping()
    public ResponseEntity<AuthorizationResponse> login(@RequestBody LoginRequest loginRequest){
            return ResponseEntity.ok(loginService.login(loginRequest));
    }
}
