package com.project.carshare.user.context.admin;

import com.project.carshare.user.context.admin.dto.VerificationRequest;
import com.project.carshare.user.context.user.dto.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/user/admin")
@RequiredArgsConstructor
public class AdminEndpoint {

    private final AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<List<UserInfoResponse>> getUserList() {
        return ResponseEntity.ok(adminService.userList());
    }

    @GetMapping("/users/verification")
    public ResponseEntity<List<UserInfoResponse>> getUserForVerificationList() {
        return ResponseEntity.ok(adminService.userForVerificationList());
    }

    @PostMapping("/user/{userId}/verify")
    public ResponseEntity<Void> verifyUser(@PathVariable String userId,
                                           @RequestBody VerificationRequest request) {
        adminService.verifyUser(userId, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user/{userId}/lock")
    public ResponseEntity<Void> lockUser(@PathVariable String userId) {
        adminService.lockUser(UUID.fromString(userId));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user/{userId}/unlock")
    public ResponseEntity<Void> unlockUser(@PathVariable String userId) {
        adminService.unlockUser(UUID.fromString(userId));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        adminService.deleteUser(UUID.fromString(userId));
        return ResponseEntity.ok().build();
    }
}
