package com.spring.nbcijo.repository;

import com.spring.nbcijo.entity.RefreshTokenBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenBlacklistRepository extends
    JpaRepository<RefreshTokenBlacklist, Long> {

    boolean existsByRefreshToken(String refreshToken);
}
