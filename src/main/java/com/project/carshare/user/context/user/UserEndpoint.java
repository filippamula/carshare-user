package com.project.carshare.user.context.user;

import com.project.carshare.user.context.user.dto.ChangePasswordRequest;
import com.project.carshare.user.context.user.dto.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserEndpoint {

    private final UserService userService;

    @GetMapping("/info")
    public ResponseEntity<UserInfoResponse> info() {
        return ResponseEntity.ok(userService.info());
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordRequest request) {
        userService.changePassword(request);
        return ResponseEntity.ok().build();
    }
}
