package com.spring.nbcijo.mypage;

import static org.assertj.core.api.Assertions.assertThat;

import com.spring.nbcijo.common.UserFixture;
import com.spring.nbcijo.repository.PasswordHistoryRepository;
import com.spring.nbcijo.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class PasswordHistoryRepositoryTest implements UserFixture {

    @Autowired
    PasswordHistoryRepository passwordHistoryRepository;
    @Autowired
    UserRepository userRepository;

    @DisplayName("유저 당 최신 3개의 데이터 조회")
    @Test
    void findTop3ByUserId() {
        // given
        var passwordHistory1 = MyPageTestUtils.get(PASSWORD_HISTORY1, 1L,
            PASSWORD_HISTORY1.getCreatedAt(),
            TEST_REPOSITORY_USER);
        var passwordHistory2 = MyPageTestUtils.get(PASSWORD_HISTORY2, 2L,
            PASSWORD_HISTORY2.getCreatedAt(),
            TEST_REPOSITORY_USER);
        var passwordHistory3 = MyPageTestUtils.get(PASSWORD_HISTORY3, 3L,
            PASSWORD_HISTORY3.getCreatedAt(),
            TEST_REPOSITORY_USER);
        userRepository.save(TEST_REPOSITORY_USER);
        passwordHistoryRepository.save(passwordHistory1);
        passwordHistoryRepository.save(passwordHistory2);
        passwordHistoryRepository.save(passwordHistory3);

        // when
        var result = passwordHistoryRepository.findTop3ByUserIdOrderByCreatedAtDesc(1L);

        // then
        assertThat(result.get(1).getCreatedAt()).isBefore(result.get(0).getCreatedAt());
    }
}
