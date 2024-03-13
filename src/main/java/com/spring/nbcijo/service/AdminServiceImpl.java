package com.spring.nbcijo.service;

import com.spring.nbcijo.dto.request.AdminRegisterRequestDto;
import com.spring.nbcijo.dto.response.UserResponseDto;
import com.spring.nbcijo.entity.User;
import com.spring.nbcijo.entity.UserRoleEnum;
import com.spring.nbcijo.global.enumeration.ErrorCode;
import com.spring.nbcijo.global.exception.DuplicateUsernameException;
import com.spring.nbcijo.global.exception.InvalidInputException;
import com.spring.nbcijo.repository.UserRepository;
import com.spring.nbcijo.service.contracts.AdminService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.token}")
    private String ADMIN_TOKEN;

    @Override
    public void register(AdminRegisterRequestDto requestDto) {
        String adminName = requestDto.getAdminName();
        String password = passwordEncoder.encode(requestDto.getPassword());

        validateDuplicatedName(adminName);
        validateAdminToken(requestDto.getAdminToken());

        User user = User.builder()
            .username(adminName)
            .password(password)
            .role(UserRoleEnum.ADMIN)
            .build();
        userRepository.save(user);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        List<User> all = userRepository.findAll();
        return all.stream()
            .map(UserResponseDto::new)
            .toList();
    }

    private void validateDuplicatedName(String adminName) {
        Optional<User> checkUsername = userRepository.findByUsername(adminName);
        if (checkUsername.isPresent()) {
            throw new DuplicateUsernameException(adminName);
        }
    }

    private void validateAdminToken(String requestToken) {
        if (!requestToken.equals(ADMIN_TOKEN)) {
            throw new InvalidInputException(ErrorCode.NOT_VALID_ADMIN_TOKEN);
        }
    }
}
