package com.spring.nbcijo.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.spring.nbcijo.dto.request.LoginRequestDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class LoginRequestDtoTest {

    @DisplayName("로그인 요청 Dto 생성")
    @Nested
    class createLoginRequestDto {

        @DisplayName("로그인 요청 Dto 생성 성공")
        @Test
        void createLoginRequestDto_success() {
            //given
            var loginRequestDto = new LoginRequestDto("username", "password");
            //when
            var violations = validate(loginRequestDto);
            //then
            assertThat(violations).isEmpty();
        }

        @DisplayName("로그인 요청 Dto 생성 실패")
        @Test
        void createLoginRequestDto_fail() {
            //given
            var loginRequestDto = new LoginRequestDto();
            //when
            var violations = validate(loginRequestDto);
            //then
            assertThat(violations).hasSize(2);
        }
    }

    private Set<ConstraintViolation<LoginRequestDto>> validate(
        LoginRequestDto loginRequestDto) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(loginRequestDto);
    }
}
