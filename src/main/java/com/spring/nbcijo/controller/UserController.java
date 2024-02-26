package com.spring.nbcijo.controller;

import com.spring.nbcijo.dto.request.SignupRequestDto;
import com.spring.nbcijo.dto.response.ResponseDto;
import com.spring.nbcijo.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<String>> signup(
        @RequestBody @Valid SignupRequestDto requestDto,
        BindingResult bindingResult) {
        // 입력 정보 검증
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (!fieldErrors.isEmpty()) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " : " + fieldError.getDefaultMessage());
            }
            throw new IllegalArgumentException("회원가입 실패 : 잘못된 입력 값이 있습니다.");
        }
        // 비밀번호 일치 확인
        if (!requestDto.getPassword().equals(requestDto.getPassword_confirm())) {
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
}
