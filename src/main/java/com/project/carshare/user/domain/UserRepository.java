package com.project.carshare.user.domain;

import com.project.carshare.user.domain.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(UUID uuid);
    Optional<User> findByEmail(String email);
    Optional<List<User>> findUsersByRole(Role role);
}
