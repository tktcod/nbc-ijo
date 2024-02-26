package com.spring.nbcijo.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentRequestDto {

    @NotBlank(message = "내용은 필수입니다")
    private String content;
}
