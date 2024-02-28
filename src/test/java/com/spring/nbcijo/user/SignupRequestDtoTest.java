package com.spring.nbcijo.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.spring.nbcijo.dto.request.SignupRequestDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class SignupRequestDtoTest {

    @DisplayName("회원가입 요청 Dto 생성")
    @Nested
    class createSignupRequestDto {

        @DisplayName("회원가입 요청 Dto 생성 성공")
        @Test
        void createSignupRequestDto_success() {
            //given
            var signupRequestDto = new SignupRequestDto("user1", "Password!1",
                "Password!1", "");
            //when
            var violations = validate(signupRequestDto);
            //then
            assertThat(violations).isEmpty();
        }

        @DisplayName("회원가입 요청 Dto 생성 실패")
        @Test
        void createSignupRequestDto_fail() {
            //given
            var signupRequestDto = new SignupRequestDto("", "",
                "", "");
            //when
            var violations = validate(signupRequestDto);
            //then
            assertThat(violations).hasSize(3);
        }
    }

    private Set<ConstraintViolation<SignupRequestDto>> validate(
        SignupRequestDto signupRequestDto) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(signupRequestDto);
    }
}
