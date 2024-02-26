package com.spring.nbcijo.service;

import com.spring.nbcijo.dto.request.SignupRequestDto;
import com.spring.nbcijo.entity.User;
import com.spring.nbcijo.entity.UserRoleEnum;
import com.spring.nbcijo.global.exception.DuplicateUsernameException;
import com.spring.nbcijo.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        String description = requestDto.getDescription();
//        String password = passwordEncoder.encode(requestDto.getPassword());

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
}
