package com.spring.nbcijo.repository;

import com.spring.nbcijo.entity.RefreshTokenBlacklist;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface RefreshTokenBlacklistRepository extends
    JpaRepository<RefreshTokenBlacklist, Long> {

    boolean existsByRefreshToken(String refreshToken);

    @Modifying
    @Transactional
    @Query("DELETE FROM RefreshTokenBlacklist r WHERE r.expirationDate <= :currentDate")
    void deleteExpiredTokens(@Param("currentDate") Date currentDate);

}
