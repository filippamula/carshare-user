package com.project.carshare.user.context.registration;

import com.project.carshare.user.context.registration.dto.RegisterRequest;
import com.project.carshare.user.domain.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user/register")
public class RegistrationEndpoint {

    private final RegistrationService registrationService;

    public RegistrationEndpoint(UserRepository userRepository, RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping()
    public ResponseEntity<Void> register(
            @RequestBody RegisterRequest request) {

        registrationService.register(request);
        return ResponseEntity.ok().build();
    }
}
