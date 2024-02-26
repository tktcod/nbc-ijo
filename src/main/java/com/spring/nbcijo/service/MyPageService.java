package com.spring.nbcijo.service;

import com.spring.nbcijo.dto.response.MyInformResponseDto;
import com.spring.nbcijo.entity.User;
import com.spring.nbcijo.global.enumeration.ErrorCode;
import com.spring.nbcijo.global.exception.InvalidInputException;
import com.spring.nbcijo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;

    public MyInformResponseDto getMyInform(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new InvalidInputException(ErrorCode.USER_NOT_FOUND));
        return MyInformResponseDto.builder()
            .id(user.getId())
            .username(user.getUsername())
            .role(user.getRole())
            .description(user.getDescription())
            .build();
    }
}
