package com.spring.nbcijo.service;

import com.spring.nbcijo.dto.request.UpdateDescriptionRequestDto;
import com.spring.nbcijo.dto.request.UpdatePasswordRequestDto;
import com.spring.nbcijo.dto.response.CommentResponseDto;
import com.spring.nbcijo.dto.response.MyInformResponseDto;
import com.spring.nbcijo.entity.PasswordHistory;
import com.spring.nbcijo.entity.Comment;
import com.spring.nbcijo.entity.User;
import com.spring.nbcijo.global.enumeration.ErrorCode;
import com.spring.nbcijo.global.exception.InvalidInputException;
import com.spring.nbcijo.repository.PasswordHistoryRepository;
import com.spring.nbcijo.repository.CommentRepository;
import com.spring.nbcijo.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
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
    private final PasswordHistoryRepository passwordHistoryRepository;

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
        if (isPasswordEquals(user.getPassword(), updateDescriptionRequestDto.getPassword())) {
            user.updateDescription(user, updateDescriptionRequestDto);
        } else {
            throw new InvalidInputException(ErrorCode.INVALID_PASSWORD);
        }
    }

    @Transactional
    public void updateMyPassword(User user, UpdatePasswordRequestDto updatePasswordRequestDto) {
        PasswordHistory passwordHistory = new PasswordHistory();

//        String encryptCurrentPassword = passwordEncoder.encode(
//            updatePasswordRequestDto.getCurrentPassword());
        String encryptNewPassword = passwordEncoder.encode(
            updatePasswordRequestDto.getNewPassword());

        user = userRepository.findById(user.getId())
            .orElseThrow(() -> new InvalidInputException(ErrorCode.USER_NOT_FOUND));
        if (isPasswordEquals(user.getPassword(),updatePasswordRequestDto.getCurrentPassword())
            && isPasswordPreviouslyUsed(user, updatePasswordRequestDto)) {
            user.updatePassword(encryptNewPassword);
            PasswordHistory newPasswordHistory = passwordHistory.toPasswordHistory(user,
                encryptNewPassword);
            passwordHistoryRepository.save(newPasswordHistory);
        } else if (
            isPasswordEquals(user.getPassword(), updatePasswordRequestDto.getCurrentPassword())
                && !isPasswordPreviouslyUsed(user, updatePasswordRequestDto)) {
            throw new InvalidInputException(ErrorCode.REUSED_PASSWORD);
        } else {
            throw new InvalidInputException(ErrorCode.INVALID_PASSWORD);
        }
    }

    public boolean isPasswordEquals(String password1, String password2) {
        boolean result = passwordEncoder.matches(password1, password2);
        return result;
    }

    public boolean isPasswordPreviouslyUsed(User user,
        UpdatePasswordRequestDto updatePasswordRequestDto) {
        boolean result = true;
        List<PasswordHistory> passwordList = passwordHistoryRepository.findTop3ByUserIdOrderByIdDesc(
            user);
        int count = 0;
        for (PasswordHistory password : passwordList) {
            count +=
                isPasswordEquals(updatePasswordRequestDto.getNewPassword(), password.getPassword())
                    ? 1 : 0;
        }
        result = count == 0 ? true : false;
        return result;
    }


    public List<CommentResponseDto> getMyComments(User user) {
        List<Comment> list = commentRepository.findAllByUserIdOrderByCreatedAtDesc(user.getId());
        List<CommentResponseDto> listToDtos = list.stream().map(CommentResponseDto::new).collect(
            Collectors.toList());
        return listToDtos;
    }
}
