package com.project.carshare.user.context.admin;

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
    public ResponseEntity<List<UserInfoResponse>> getUserList(){
        return ResponseEntity.ok(adminService.userList());
    }

    @PostMapping("/user/{userId}/lock")
    public ResponseEntity<Void> lockUser(@PathVariable String userId){
        adminService.lockUser(UUID.fromString(userId));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user/{userId}/unlock")
    public ResponseEntity<Void> unlockUser(@PathVariable String userId){
        adminService.unlockUser(UUID.fromString(userId));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId){
        adminService.deleteUser(UUID.fromString(userId));
        return ResponseEntity.ok().build();
    }
}
