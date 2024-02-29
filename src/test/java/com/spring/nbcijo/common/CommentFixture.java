package com.spring.nbcijo.common;

import com.spring.nbcijo.dto.request.CommentRequestDto;
import com.spring.nbcijo.dto.response.CommentResponseDto;
import com.spring.nbcijo.entity.Comment;

public interface CommentFixture extends UserFixture {

    Long TEST_COMMENT_ID = 1L;
    String TEST_COMMENT_CONTENT = "content";
    Long TEST_ANOTHER_COMMENT_ID = 2L;

    Comment TEST_COMMENT = Comment.builder()
        .content(TEST_COMMENT_CONTENT)
        .build();

    Comment TEST_ANOTHER_COMMENT = Comment.builder()
        .content(ANOTHER_PREFIX + TEST_COMMENT_CONTENT)
        .build();

    Comment MY_TEST_COMMENT1 = Comment.builder()
        .content(TEST_COMMENT_CONTENT)
        .user(TEST_USER)
        .build();

    Comment MY_TEST_COMMENT2 = Comment.builder()
        .content(TEST_ANOTHER_COMMENT.getContent())
        .user(TEST_USER)
        .build();

    CommentRequestDto TEST_COMMENT_REQUEST_DTO = CommentRequestDto.builder()
        .content(TEST_COMMENT_CONTENT)
        .build();

    CommentRequestDto TEST_ANOTHER_COMMENT_REQUEST_DTO = CommentRequestDto.builder()
        .content(ANOTHER_PREFIX + TEST_COMMENT_CONTENT)
        .build();

    CommentResponseDto commentResponseDto1 = new CommentResponseDto(MY_TEST_COMMENT1);
    CommentResponseDto commentResponseDto2 = new CommentResponseDto(MY_TEST_COMMENT2);
}
