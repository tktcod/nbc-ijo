package com.spring.nbcijo.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.spring.nbcijo.dto.request.AdminLoginRequestDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class AdminLoginRequestDtoTest {

    @DisplayName("관리자 로그인 요청 Dto 생성")
    @Nested
    class createAdminLoginRequestDto {

        @DisplayName("관리자 로그인 요청 Dto 생성 성공")
        @Test
        void createAdminLoginRequestDto_success() {
            //given
            var requestDto = new AdminLoginRequestDto("admin", "Dd1@Dd1@");
            //when
            var violations = validate(requestDto);
            //then
            assertThat(violations).isEmpty();
        }

        @DisplayName("관리자 로그인 요청 Dto 생성 실패")
        @Test
        void createAdminLoginRequestDto_fail() {
            //given
            var requestDto = new AdminLoginRequestDto("", "");
            //when
            var violations = validate(requestDto);
            //then
            assertThat(violations).hasSize(2);
        }
    }

    private Set<ConstraintViolation<AdminLoginRequestDto>> validate(
        AdminLoginRequestDto requestDto) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(requestDto);
    }
}
