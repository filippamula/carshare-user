package com.project.carshare.user.context.admin;

import com.project.carshare.user.context.user.dto.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/user/admin")
@RequiredArgsConstructor
public class AdminEndpoint {

    private final AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<List<UserInfoResponse>> getUserList(){
        return ResponseEntity.ok(adminService.userList());
    }
}
