package com.kamilwnek.lift_management.repository;

import com.kamilwnek.lift_management.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

    @Query(value = "SELECT * FROM refresh_token rt " +
            "WHERE rt.token = ?1 and rt.device_name = ?2",
        nativeQuery = true)
    Optional<RefreshToken> findByTokenAndDeviceName(String refreshToken, String device);
}
