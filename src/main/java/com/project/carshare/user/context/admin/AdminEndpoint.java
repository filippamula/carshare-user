package com.project.carshare.user.context.admin;

import com.project.carshare.user.context.admin.dto.VerificationRequest;
import com.project.carshare.user.context.user.dto.UserInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminEndpoint {

    private final AdminService adminService;

    @Operation(description = "Returns list of unarchived users, required role admin")
    @GetMapping("/users")
    public ResponseEntity<List<UserInfoResponse>> getUserList() {
        return ResponseEntity.ok(adminService.userList());
    }

    @Operation(description = "Returns list of ready for verification, required role admin")
    @GetMapping("/users/verification")
    public ResponseEntity<List<UserInfoResponse>> getUserForVerificationList() {
        return ResponseEntity.ok(adminService.userForVerificationList());
    }

    @Operation(description = "Verify user, required role admin")
    @PostMapping("/user/{userId}/verify")
    public ResponseEntity<Void> verifyUser(@PathVariable UUID userId,
                                           @RequestBody VerificationRequest request) {
        adminService.verifyUser(userId, request);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "Lock user, required role admin")
    @PostMapping("/user/{userId}/lock")
    public ResponseEntity<Void> lockUser(@PathVariable String userId) {
        adminService.lockUser(UUID.fromString(userId));
        return ResponseEntity.ok().build();
    }

    @Operation(description = "Unlock user, required role admin")
    @PostMapping("/user/{userId}/unlock")
    public ResponseEntity<Void> unlockUser(@PathVariable String userId) {
        adminService.unlockUser(UUID.fromString(userId));
        return ResponseEntity.ok().build();
    }

    @Operation(description = "Delete user, required role admin")
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        adminService.deleteUser(UUID.fromString(userId));
        return ResponseEntity.ok().build();
    }
}
