package com.spring.nbcijo.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.spring.nbcijo.dto.request.AdminRegisterRequestDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class AdminRegisterRequestDtoTest {

    @DisplayName("관리자 등록 요청 Dto 생성")
    @Nested
    class createAdminRegisterRequestDto {

        @DisplayName("관리자 등록 요청 Dto 생성 성공")
        @Test
        void createAdminRegisterRequestDto_success() {
            //given
            var requestDto = new AdminRegisterRequestDto("admin", "Dd1@Dd1@",
                "Dd1@Dd1@", "admin_token");
            //when
            var violations = validate(requestDto);
            //then
            assertThat(violations).isEmpty();
        }

        @DisplayName("관리자 등록 요청 Dto 생성 실패")
        @Test
        void createAdminRegisterRequestDto_fail() {
            //given
            var requestDto = new AdminRegisterRequestDto("", "",
                "", "");
            //when
            var violations = validate(requestDto);
            //then
            assertThat(violations).hasSize(3);
        }
    }

    private Set<ConstraintViolation<AdminRegisterRequestDto>> validate(
        AdminRegisterRequestDto requestDto) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(requestDto);
    }
}
