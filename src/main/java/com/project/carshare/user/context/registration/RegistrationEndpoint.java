package com.project.carshare.user.context.registration;

import com.project.carshare.user.domain.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user/registration")
public class RegistrationEndpoint {

    private final UserRepository userRepository;

    public RegistrationEndpoint(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping()
    public String x(){
        return "H@@H@H@H@";
    }
}
