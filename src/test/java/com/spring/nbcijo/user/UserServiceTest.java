package com.spring.nbcijo.user;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.spring.nbcijo.common.UserFixture;
import com.spring.nbcijo.entity.PasswordHistory;
import com.spring.nbcijo.entity.RefreshTokenBlacklist;
import com.spring.nbcijo.entity.User;
import com.spring.nbcijo.global.exception.DuplicateUsernameException;
import com.spring.nbcijo.jwt.JwtUtil;
import com.spring.nbcijo.repository.PasswordHistoryRepository;
import com.spring.nbcijo.repository.RefreshTokenBlacklistRepository;
import com.spring.nbcijo.repository.UserRepository;
import com.spring.nbcijo.service.UserService;
import java.util.Date;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest implements UserFixture {

    @InjectMocks
    UserService userService;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    RefreshTokenBlacklistRepository refreshTokenBlacklistRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordHistoryRepository passwordHistoryRepository;

    @Mock
    JwtUtil jwtUtil;

    @Nested
    @DisplayName("회원가입")
    class signUp {

        @Test
        @DisplayName("회원가입 성공")
        void signUp_success() {
            //given
            //when & then
            assertDoesNotThrow(() -> userService.signup(TEST_SIGN_UP_REQUEST_DTO));
            verify(passwordHistoryRepository, times(1))
                .save(any(PasswordHistory.class));
            verify(userRepository, times(1))
                .save(any(User.class));
        }

        @Test
        @DisplayName("회원가입 실패 - 중복된 이름")
        void signUp_fail() {
            //given
            var testUser = TEST_USER;
            given(userRepository.findByUsername(testUser.getUsername()))
                .willReturn(Optional.of(testUser));

            //when & then
            assertThatThrownBy(() -> userService.signup(TEST_SIGN_UP_REQUEST_DTO))
                .isInstanceOf(DuplicateUsernameException.class);
        }
    }

    @Nested
    @DisplayName("로그아웃")
    class logout {

        @Test
        @DisplayName("로그아웃 성공")
        void logout_success() {
            // given
            String refreshToken = "your_refresh_token_value";
            given(jwtUtil.extractExpirationDateFromToken(any(String.class))).willReturn(new Date());

            // when - then
            assertDoesNotThrow(
                () -> userService.logout(refreshToken)
            );
            verify(refreshTokenBlacklistRepository, times(1))
                .save(any(RefreshTokenBlacklist.class));
        }

        @Test
        @DisplayName("로그아웃 실패")
        void logout_fail() {
            // given
            String refreshToken = "invalid_refresh_token_value";
            given(jwtUtil.extractExpirationDateFromToken(any(String.class))).willReturn(null);

            //when & then
            assertThatThrownBy(() -> userService.logout(refreshToken))
                .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
