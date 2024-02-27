package com.spring.nbcijo.service;

import com.spring.nbcijo.dto.request.UpdateDescriptionRequestDto;
import com.spring.nbcijo.dto.response.CommentResponseDto;
import com.spring.nbcijo.dto.response.MyInformResponseDto;
import com.spring.nbcijo.entity.Comment;
import com.spring.nbcijo.entity.User;
import com.spring.nbcijo.global.enumeration.ErrorCode;
import com.spring.nbcijo.global.exception.InvalidInputException;
import com.spring.nbcijo.repository.CommentRepository;
import com.spring.nbcijo.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;

    public MyInformResponseDto getMyInform(User user) {
        user = userRepository.findById(user.getId())
            .orElseThrow(() -> new InvalidInputException(ErrorCode.USER_NOT_FOUND));
        return MyInformResponseDto.builder()
            .id(user.getId())
            .username(user.getUsername())
            .role(user.getRole())
            .description(user.getDescription())
            .build();
    }

    @Transactional
    public void updateMyDescription(User user,
        UpdateDescriptionRequestDto updateDescriptionRequestDto) {
        user = userRepository.findById(user.getId())
            .orElseThrow(() -> new InvalidInputException(ErrorCode.USER_NOT_FOUND));
        if (passwordEncoder.matches(updateDescriptionRequestDto.getPassword(),
            user.getPassword())) {
            user.updateDescription(user, updateDescriptionRequestDto);
        } else {
            throw new InvalidInputException(ErrorCode.INVALID_PASSWORD);
        }
    }

    public List<CommentResponseDto> getMyComments(User user) {
        List<Comment> list = commentRepository.findAllByUserIdOrderByCreatedAtDesc(user.getId());
        List<CommentResponseDto> listToDtos = list.stream().map(CommentResponseDto::new).collect(
            Collectors.toList());
        return listToDtos;
    }
}
