package com.spring.nbcijo.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.spring.nbcijo.common.ControllerTest;
import com.spring.nbcijo.controller.UserController;
import com.spring.nbcijo.dto.request.LoginRequestDto;
import com.spring.nbcijo.dto.request.SignupRequestDto;
import com.spring.nbcijo.global.ControllerAdvice;
import com.spring.nbcijo.global.exception.DuplicateUsernameException;
import com.spring.nbcijo.jwt.JwtUtil;
import com.spring.nbcijo.security.UserDetailsImpl;
import com.spring.nbcijo.service.UserServiceImpl;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

@WebMvcTest(UserController.class)
@Import({JwtUtil.class, ControllerAdvice.class})
public class UserControllerTest extends ControllerTest {

    @MockBean
    private UserServiceImpl userService;

    @Nested
    @DisplayName("회원가입 요청")
    class signUp {

        @DisplayName("회원가입 요청 성공")
        @Test
        void signUp_success() throws Exception {
            //given //when
            var action = mockMvc.perform(post("/user/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TEST_SIGN_UP_REQUEST_DTO)));

            //then
            action.andExpect(status().isOk());
            verify(userService, times(1))
                .signup(any(SignupRequestDto.class));
        }

        @DisplayName("회원가입 요청 실패")
        @Test
        void signUp_fail() throws Exception {
            //given
            doThrow(new DuplicateUsernameException(TEST_USER_NAME)).when(userService)
                .signup(any(SignupRequestDto.class));

            //when
            var action = mockMvc.perform(post("/user/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TEST_SIGN_UP_REQUEST_DTO)));

            //then
            action.andExpect(status().isBadRequest());
        }
    }


    @Nested
    @DisplayName("로그인 요청")
    class login {

        @DisplayName("로그인 요청 성공")
        @Test
        void login_success() throws Exception {
            // given
            UserDetails userDetails = new UserDetailsImpl(TEST_USER);
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());
            LoginRequestDto requestDto = new LoginRequestDto("testuser", "password");

            given(userService.login(anyString(), anyString())).willReturn(authentication);

            // when
            var action = mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)));

            //then
            action.andExpect(status().isOk());
        }

        @DisplayName("로그인 요청 실패")
        @Test
        void login_failed() throws Exception {
            // given
            LoginRequestDto requestDto = new LoginRequestDto("testuser", "invalidPassword");

            doThrow(new BadCredentialsException("Bad credentials")).when(userService)
                .login(any(String.class), any(String.class));

            // when
            var action = mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)));

            //then
            System.out.println(action);
            action.andExpect(status().isUnauthorized());
        }
    }

    @Nested
    @DisplayName("로그아웃 요청")
    class logout {

        @DisplayName("로그아웃 요청 성공")
        @Test
        void logout_success() throws Exception {
            // given
            String refreshToken = "your_refresh_token_value";
            Cookie cookie = new Cookie("refreshToken", refreshToken);

            doNothing().when(userService).logout(any(String.class));

            // when
            var action = mockMvc.perform(post("/user/logout")
                .cookie(cookie) // 쿠키를 요청에 포함
                .accept(MediaType.APPLICATION_JSON));

            //then
            action.andExpect(status().isOk());
            verify(userService, times(1)).logout(refreshToken);
        }

        @DisplayName("로그아웃 요청 실패")
        @Test
        void logout_failed() throws Exception {
            // given
            doThrow(new IllegalArgumentException()).when(userService)
                .logout(any(String.class));

            // when
            var action = mockMvc.perform(post("/user/logout")
                .accept(MediaType.APPLICATION_JSON));

            //then
            action.andExpect(status().isBadRequest());
        }
    }
}
