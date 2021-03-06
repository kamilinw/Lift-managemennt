package com.kamilwnek.lift_management.repository;

import com.kamilwnek.lift_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);

    @Query("SELECT " +
                "CASE " +
                    "WHEN COUNT(id) = 0 THEN TRUE " +
                    "ELSE FALSE " +
                "END " +
            "FROM User u " +
            "WHERE u.username = ?1")
    Boolean isUsernameUnique(String username);

    @Query("SELECT " +
                "CASE " +
                    "WHEN COUNT(id) = 0 THEN TRUE " +
                    "ELSE FALSE " +
                "END " +
            "FROM User u " +
            "WHERE u.email = ?1")
    Boolean isEmailUnique(String email);
}
