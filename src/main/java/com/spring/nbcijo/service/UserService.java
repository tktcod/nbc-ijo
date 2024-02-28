package com.spring.nbcijo.service;

import com.spring.nbcijo.dto.request.SignupRequestDto;
import com.spring.nbcijo.entity.RefreshTokenBlacklist;
import com.spring.nbcijo.entity.User;
import com.spring.nbcijo.entity.UserRoleEnum;
import com.spring.nbcijo.global.exception.DuplicateUsernameException;
import com.spring.nbcijo.jwt.JwtUtil;
import com.spring.nbcijo.repository.RefreshTokenBlacklistRepository;
import com.spring.nbcijo.repository.UserRepository;
import java.util.Date;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RefreshTokenBlacklistRepository refreshTokenBlacklistRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String description = requestDto.getDescription();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new DuplicateUsernameException(username);
        }

        // 유저 등록
        User user = User.builder()
            .username(username)
            .password(password)
            .role(UserRoleEnum.USER)
            .description(description)
            .build();
        userRepository.save(user);
    }

    public void logout(String refreshToken) {
        Date expirationDate = jwtUtil.extractExpirationDateFromToken(refreshToken);
        if (expirationDate == null) {
            throw new IllegalArgumentException("로그아웃 실패 : Refresh Token이 유효하지 않습니다.");
        }

        RefreshTokenBlacklist refreshTokenBlacklist = RefreshTokenBlacklist.builder()
            .refreshToken(refreshToken)
            .expirationDate(expirationDate)
            .build();
        refreshTokenBlacklistRepository.save(refreshTokenBlacklist);
    }
}
