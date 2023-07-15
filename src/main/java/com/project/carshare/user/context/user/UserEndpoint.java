package com.project.carshare.user.context.user;

import com.project.carshare.user.context.user.dto.ChangePasswordRequest;
import com.project.carshare.user.context.user.dto.UserInfoResponse;
import com.project.carshare.user.context.user.dto.VerificationInfoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping()
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

    @PostMapping("/verification/info")
    public ResponseEntity<Void> addVerification(@RequestBody VerificationInfoRequest request) {
        userService.addVerificationInfo(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verification/driving-license")
    public ResponseEntity<Void> addDrivingLicense(@RequestBody MultipartFile file) {

        userService.addDrivingLicense(extractBytes(file));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verification/send")
    public ResponseEntity<Void> sendToVerification() {
        userService.sendToVerification();
        return ResponseEntity.ok().build();
    }

    private byte[] extractBytes(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException("Error parsing file");
        }
    }
}
