package com.spring.nbcijo.Scheduler;

import com.spring.nbcijo.repository.RefreshTokenBlacklistRepository;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j(topic = "RefreshTokenCleanupScheduler")
@Component
@RequiredArgsConstructor
public class RefreshTokenCleanupScheduler {

    private final RefreshTokenBlacklistRepository refreshTokenBlacklistRepository;

    // 초, 분, 시, 일, 월, 주
    @Scheduled(cron = "0 0 0 * * *") // 매일 자정에 실행
    public void cleanupExpiredTokens() {
        // 만료된 토큰을 삭제
        log.info("Refresh Token cleanup");
        Date currentDate = new Date();
        refreshTokenBlacklistRepository.deleteExpiredTokens(currentDate);
    }
}
