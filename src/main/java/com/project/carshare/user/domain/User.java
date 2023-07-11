package com.project.carshare.user.domain;

import com.project.carshare.user.domain.enums.Role;
import com.project.carshare.user.domain.enums.UserStatus;
import com.project.carshare.user.domain.enums.VerificationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "APP_USERS")
@Entity
@Data
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    @Enumerated(EnumType.STRING)
    private VerificationStatus verificationStatus;
    private byte[] drivingLicense;
    private String pesel;
    private LocalDate dateOfBirth;
    private String phoneNo;
    private String lastToken;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status.equals(UserStatus.ACTIVE);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean isActive() {
        return status.equals(UserStatus.ACTIVE);
    }

    public boolean isArchived() {
        return status.equals(UserStatus.ARCHIVED);
    }

    public boolean isLocked() {
        return status.equals(UserStatus.LOCKED);
    }

    public boolean isVerified() {
        return verificationStatus.equals(VerificationStatus.VERIFIED);
    }

    public boolean isSentToVerification() {
        return verificationStatus.equals(VerificationStatus.PENDING);
    }

    public boolean isReadyForVerification() {
        return pesel != null && phoneNo != null && dateOfBirth != null && drivingLicense != null;
    }

    public boolean isAdmin() {
        return role.equals(Role.ADMIN);
    }
}
