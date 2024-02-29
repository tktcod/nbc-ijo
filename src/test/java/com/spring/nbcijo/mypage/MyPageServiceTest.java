package com.spring.nbcijo.mypage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import com.spring.nbcijo.common.CommentFixture;
import com.spring.nbcijo.common.PostFixture;
import com.spring.nbcijo.common.UserFixture;
import com.spring.nbcijo.dto.request.UpdateDescriptionRequestDto;
import com.spring.nbcijo.dto.response.MyInformResponseDto;
import com.spring.nbcijo.entity.User;
import com.spring.nbcijo.global.enumeration.ErrorCode;
import com.spring.nbcijo.global.exception.InvalidInputException;
import com.spring.nbcijo.repository.CommentRepository;
import com.spring.nbcijo.repository.PasswordHistoryRepository;
import com.spring.nbcijo.repository.PostRepository;
import com.spring.nbcijo.repository.UserRepository;
import com.spring.nbcijo.service.MyPageService;
import java.util.Optional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class MyPageServiceTest implements UserFixture, PostFixture, CommentFixture {

    @InjectMocks
    MyPageService myPageService;

    @Mock
    UserRepository userRepository;
    @Mock
    PostRepository postRepository;
    @Mock
    CommentRepository commentRepository;
    @Mock
    PasswordHistoryRepository passwordHistoryRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    @Disabled
    @DisplayName("마이페이지 내 정보 조회 성공")
    @Test
    void getMyInform_success() {
        // given
        var testUser = MyPageTestUtils.get(TEST_USER);
        given(userRepository.findById(eq(TEST_USER_ID))).willReturn(
            Optional.of(testUser));
        // when
        var result = myPageService.getMyInform(TEST_USER);

        // then
        assertThat(result).isEqualTo(new MyInformResponseDto(testUser));
    }

    @Disabled
    @DisplayName("한 줄 소개 수정")
    @Test
    void updateMyDescription_success() {
        // given
//        ReflectionTestUtils.setField(TEST_USER2, User.class, "id", TEST_USER_ID, Long.class);
        var testUser = MyPageTestUtils.get(TEST_USER);
        var requestDto = new UpdateDescriptionRequestDto(TEST_USER_PASSWORD, TEST_DESCRIPTION_UPDATE_REQUEST.getUpdateDescription());
        given(userRepository.findById(any())).willReturn(
            Optional.of(testUser));

        // when
//        var request = new UpdateDescriptionRequestDto(TEST_USER_PASSWORD,
//            TEST_DESCRIPTION_UPDATE_REQUEST.getUpdateDescription());

        // when - then
        assertDoesNotThrow(() ->
            myPageService.updateMyDescription(TEST_USER,requestDto));
    }

    @Disabled
    @DisplayName("비밀번호 수정")
    @Test
    void updateMyPassword_success() {
        // given
        ReflectionTestUtils.setField(TEST_USER, User.class, "id", TEST_USER_ID, Long.class);
        var testUser = MyPageTestUtils.get(TEST_USER);
        given(userRepository.save(TEST_USER)).willReturn(testUser);
        given(userRepository.findById(eq(TEST_USER_ID))).willReturn(
            Optional.of(testUser));
        // when
        var request = new UpdateDescriptionRequestDto(TEST_USER_PASSWORD,
            TEST_PASSWORD_UPDATE_REQUEST.getNewPassword());

        // then
        assertDoesNotThrow(() ->
            myPageService.updateMyPassword(TEST_USER,TEST_PASSWORD_UPDATE_REQUEST));
    }

    @Disabled
    @DisplayName("비밀번호 수정 실패")
    @Test
    void updateMyPassword_fail1() {
        // given
        ReflectionTestUtils.setField(TEST_USER, User.class, "id", TEST_USER_ID, Long.class);
        var testUser = MyPageTestUtils.get(TEST_USER);
//        given(userRepository.save(TEST_USER2)).willReturn(testUser);
        given(userRepository.findById(eq(TEST_USER_ID))).willReturn(
            Optional.of(testUser));
        // when
        var request = new UpdateDescriptionRequestDto(TEST_USER_PASSWORD_FAIL,
            TEST_PASSWORD_UPDATE_REQUEST.getNewPassword());

//        // then
//        assertThrows(InvalidInputException.class, () -> );
    }

}
