package com.spring.nbcijo.controller;

import com.spring.nbcijo.dto.request.LoginRequestDto;
import com.spring.nbcijo.dto.request.SignupRequestDto;
import com.spring.nbcijo.dto.response.ResponseDto;
import com.spring.nbcijo.entity.UserRoleEnum;
import com.spring.nbcijo.jwt.JwtUtil;
import com.spring.nbcijo.security.UserDetailsImpl;
import com.spring.nbcijo.service.UserServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userService;
    private final JwtUtil jwtUtil;

    @GetMapping("/login-page")
    public String loginPage() {
        return "index";
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<String>> login(
        @RequestBody @Valid LoginRequestDto requestDto,
        HttpServletResponse response) {
        // 사용자 인증
        Authentication authentication = userService.login(requestDto.getUsername(), requestDto.getPassword());
        String username = ((UserDetailsImpl) authentication.getPrincipal()).getUsername();
        UserRoleEnum role = ((UserDetailsImpl) authentication.getPrincipal()).getUser().getRole();

        // AccessToken 생성
        String token = jwtUtil.createAccessToken(username, role);

        // RefreshToken 생성
        String refreshToken = jwtUtil.createRefreshToken(username, role);

        // AccessToken을 Response Header에 추가
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

        // 리프레시 토큰을 쿠키에 담아 설정
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setMaxAge((int) (jwtUtil.REFRESH_TOKEN_TIME / 1000)); // 쿠키 유효 시간 설정
        refreshTokenCookie.setHttpOnly(true); // XSS 방지
        refreshTokenCookie.setPath("/"); // 쿠키 경로 설정
        response.addCookie(refreshTokenCookie); // 쿠키를 응답에 추가

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.<String>builder()
            .statusCode(HttpStatus.OK.value())
            .message("로그인이 완료되었습니다.")
            .build());
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<String>> signup(
        @RequestBody @Valid SignupRequestDto requestDto) {

        // 비밀번호 일치 확인
        if (!requestDto.getPassword().equals(requestDto.getPasswordConfirm())) {
            throw new IllegalArgumentException("회원가입 실패 : 비밀번호가 일치하지 않습니다.");
        }
        // 회원가입
        userService.signup(requestDto);

        ResponseDto<String> responseDto = ResponseDto.<String>builder()
            .statusCode(HttpStatus.OK.value())
            .message("회원가입이 완료되었습니다.")
            .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDto<String>> logout(
        @CookieValue(value = "refreshToken", defaultValue = "") String refreshToken) {
        if (refreshToken.isEmpty()) {
            throw new IllegalArgumentException("로그아웃 실패 : Refresh Token을 찾을 수 없습니다.");
        }

        userService.logout(refreshToken);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.<String>builder()
            .statusCode(HttpStatus.OK.value())
            .message("로그아웃이 완료되었습니다.")
            .build());
    }
}
