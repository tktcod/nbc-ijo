package com.spring.nbcijo.post;

import static org.assertj.core.api.Assertions.assertThat;

import com.spring.nbcijo.common.PostFixture;
import com.spring.nbcijo.dto.request.PostRequestDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class PostRequestDtoTest implements PostFixture {

    @DisplayName("게시글 요청 Dto 생성")
    @Nested
    class createPostRequestDto {

        @DisplayName("게시글 요청 Dto 생성 성공")
        @Test
        void createPostRequestDto_success() {
            // given
            var postRequestDto = new PostRequestDto(TEST_POST_TITLE, TEST_POST_CONTENT);

            // when
            var violations = validate(postRequestDto);

            // then
            assertThat(violations).isEmpty();
        }

        @DisplayName("게시글 요청 Dto 생성 실패 - 비어있는 title")
        @Test
        void createPostRequestDto_fail_nullTitle() {
            // given
            var postRequestDto = new PostRequestDto("", TEST_POST_CONTENT);

            // when
            var violations = validate(postRequestDto);

            // then
            assertThat(violations).hasSize(1);
            assertThat(violations)
                .extracting("message")
                .contains("제목은 필수입니다.");
        }

        @DisplayName("게시글 요청 Dto 생성 실패 - 비어있는 content")
        @Test
        void createPostRequestDto_fail_nullContent() {
            // given
            var postRequestDto = new PostRequestDto(TEST_POST_TITLE, "");

            // when
            var violations = validate(postRequestDto);

            // then
            assertThat(violations).hasSize(1);
            assertThat(violations)
                .extracting("message")
                .contains("내용은 필수입니다.");
        }
    }

    private Set<ConstraintViolation<PostRequestDto>> validate(PostRequestDto postRequestDto) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(postRequestDto);
    }
}
