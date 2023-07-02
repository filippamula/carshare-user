package com.project.carshare.user.domain;

import com.project.carshare.user.domain.enums.Role;
import com.project.carshare.user.domain.enums.Status;
import jakarta.annotation.Nullable;
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
@Table(name = "APP_USER")
@Entity
@Data
@Builder
public class User implements UserDetails {

    @Id
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private Status status;
    private boolean verified;
    @Lob
    private byte[] drivingLicense;
    private String pesel;
    private LocalDate dateOfBirth;
    private String phoneNo;

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
        return status.equals(Status.ACTIVE);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean isArchived() {
        return status.equals(Status.ARCHIVED);
    }

    public boolean isLocked() {
        return status.equals(Status.LOCKED);
    }
}
