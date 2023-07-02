package com.project.carshare.user.context.admin;

import com.project.carshare.user.context.user.dto.UserInfoResponse;
import com.project.carshare.user.domain.UserRepository;
import com.project.carshare.user.domain.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    public List<UserInfoResponse> userList() {
        return userRepository.findUsersByRole(Role.USER)
                .orElseThrow(() -> new RuntimeException("No user found"))
                .stream().map(it -> UserInfoResponse.builder()
                        .id(it.getId())
                        .email(it.getEmail())
                        .firstName(it.getFirstName())
                        .lastName(it.getLastName())
                        .build()).toList();
    }
}
