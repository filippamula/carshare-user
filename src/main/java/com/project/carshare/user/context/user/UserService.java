package com.project.carshare.user.context.user;

import com.project.carshare.user.context.user.dto.ChangePasswordRequest;
import com.project.carshare.user.context.user.dto.UserInfoResponse;
import com.project.carshare.user.context.user.dto.VerificationInfoRequest;
import com.project.carshare.user.domain.UserRepository;
import com.project.carshare.user.domain.enums.VerificationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserInfoResponse info(){
       var email =  SecurityContextHolder.getContext().getAuthentication().getName();

       var user = userRepository.findByEmail(email)
               .orElseThrow(() -> new RuntimeException("User not found"));

       return UserInfoResponse.builder()
               .id(user.getId())
               .firstName(user.getFirstName())
               .lastName(user.getLastName())
               .email(user.getEmail())
               .verified(user.getVerificationStatus())
               .drivingLicense(user.getDrivingLicense())
               .pesel(user.getPesel())
               .dateOfBirth(user.getDateOfBirth())
               .phoneNo(user.getPhoneNo())
               .build();
    }

    public void changePassword(ChangePasswordRequest request) {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();

        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())){
            throw new RuntimeException("Incorrect password");
        }
        if(!request.getNewPassword().equals(request.getConfirmNewPassword())){
            throw new RuntimeException("New passwords don't match");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    public void addVerificationInfo(VerificationInfoRequest request) {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();

        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPesel(request.getPesel());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setPhoneNo(request.getPhoneNo());

        userRepository.save(user);
    }

    public void addDrivingLicense(byte[] bytes) {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();

        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setDrivingLicense(bytes);

        userRepository.save(user);
    }

    public void sendToVerification() {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();

        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!user.isReadyForVerification()){
            throw new RuntimeException("User not ready for verification, fill in missing data");
        }

        user.setVerificationStatus(VerificationStatus.PENDING);

        userRepository.save(user);
    }
}
