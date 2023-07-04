package com.project.carshare.user.context.admin;

import com.project.carshare.user.context.admin.dto.VerificationRequest;
import com.project.carshare.user.context.user.dto.UserInfoResponse;
import com.project.carshare.user.domain.UserRepository;
import com.project.carshare.user.domain.enums.Role;
import com.project.carshare.user.domain.enums.UserStatus;
import com.project.carshare.user.domain.enums.VerificationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    public List<UserInfoResponse> userList() {
        return userRepository.findUsersByRole(Role.USER)
                .orElseThrow(() -> new RuntimeException("No user found"))
                .stream().filter(it -> !it.getStatus().equals(UserStatus.ARCHIVED))
                .map(it -> UserInfoResponse.builder()
                        .id(it.getId())
                        .email(it.getEmail())
                        .firstName(it.getFirstName())
                        .lastName(it.getLastName())
                        .build())
                .toList();
    }

    public void deleteUser(UUID uuid) {
        var user = userRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.isArchived()) {
            throw new RuntimeException("User not found");
        }

        user.setStatus(UserStatus.ARCHIVED);
        userRepository.save(user);
    }

    public void lockUser(UUID uuid) {
        var user = userRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.isArchived()) {
            throw new RuntimeException("User not found");
        }
        if (user.isLocked()) {
            throw new RuntimeException("User already locked");
        }

        user.setStatus(UserStatus.LOCKED);
        userRepository.save(user);
    }

    public void unlockUser(UUID uuid) {
        var user = userRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.isArchived()) {
            throw new RuntimeException("User not found");
        }
        if (!user.isLocked()) {
            throw new RuntimeException("User already active");
        }

        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
    }

    public List<UserInfoResponse> userForVerificationList() {
        return userRepository.findUsersByRole(Role.USER)
                .orElseThrow(() -> new RuntimeException("No user found"))
                .stream()
                .filter(it -> !it.getStatus().equals(UserStatus.ARCHIVED))
                .filter(it -> it.getVerificationStatus().equals(VerificationStatus.PENDING))
                .map(it -> UserInfoResponse.builder()
                        .id(it.getId())
                        .email(it.getEmail())
                        .firstName(it.getFirstName())
                        .lastName(it.getLastName())
                        .drivingLicense(it.getDrivingLicense())
                        .pesel(it.getPesel())
                        .dateOfBirth(it.getDateOfBirth())
                        .phoneNo(it.getPhoneNo())
                        .build())
                .toList();
    }

    public void verifyUser(String userId, VerificationRequest request) {
        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(user.isVerified()){
            throw new RuntimeException("User already verified");
        }
        if(request.isVerifiedSuccessfully()){
            user.setVerificationStatus(VerificationStatus.VERIFIED);
        }
        if(!request.isVerifiedSuccessfully()){
            user.setVerificationStatus(VerificationStatus.NEGATIVE_VERIFICATION);
        }

        userRepository.save(user);
    }
}
