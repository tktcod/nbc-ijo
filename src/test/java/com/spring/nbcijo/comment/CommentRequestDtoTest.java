package com.spring.nbcijo.comment;

import static org.assertj.core.api.Assertions.*;

import com.spring.nbcijo.common.CommentFixture;
import com.spring.nbcijo.dto.request.CommentRequestDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CommentRequestDtoTest implements CommentFixture {

    @DisplayName("댓글 요청 Dto 생성")
    @Nested
    class createCommentRequestDto {

        @DisplayName("댓글 요청 Dto 생성 성공")
        @Test
        void createCommentRequestDto_success() {
            //given
            var commentRequestDto = new CommentRequestDto(TEST_COMMENT_CONTENT);
            //when
            var violations = validate(commentRequestDto);
            //then
            assertThat(violations).isEmpty();
        }

        @DisplayName("댓글 요청 Dto 생성 실패")
        @Test
        void createCommentRequestDto_fail() {
            //given
            var commentRequestDto = new CommentRequestDto("");
            //when
            var violations = validate(commentRequestDto);
            //then
            assertThat(violations).hasSize(1);
            assertThat(violations)
                .extracting("message")
                .contains("내용은 필수입니다");
        }
    }

    private Set<ConstraintViolation<CommentRequestDto>> validate(
        CommentRequestDto commentRequestDto) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(commentRequestDto);
    }
}
