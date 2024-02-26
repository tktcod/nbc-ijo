package com.spring.nbcijo.mypage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import com.spring.nbcijo.common.UserFixture;
import com.spring.nbcijo.global.exception.InvalidInputException;
import com.spring.nbcijo.repository.UserRepository;
import com.spring.nbcijo.service.MyPageService;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MyPageServiceTest implements UserFixture {

    @InjectMocks
    MyPageService myPageService;

    @Mock
    UserRepository userRepository;

    @Test
    @DisplayName("유저 정보 조회 성공")
    void getMyInform_Success() {
        //given
        given(userRepository.findById(eq(TEST_USER_ID))).willReturn(Optional.of(TEST_USER));

        // when & then
        assertDoesNotThrow(() -> myPageService.getMyInform(TEST_USER_ID));
    }

    @Test
    @DisplayName("유저 정보 조회 실패")
    void getMyInform_fail() {
        //given
        given(userRepository.findById(eq(TEST_FAIL_USER_ID))).willReturn(Optional.empty());

        // when & then
        assertThrows(InvalidInputException.class,
            () -> myPageService.getMyInform(TEST_FAIL_USER_ID));
    }
}
