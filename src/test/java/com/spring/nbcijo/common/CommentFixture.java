package com.spring.nbcijo.common;

import com.spring.nbcijo.entity.Comment;

public interface CommentFixture extends UserFixture {

    Long TEST_COMMENT_ID = 1L;
    String TEST_COMMENT_CONTENT = "content";

    Comment TEST_COMMENT = Comment.builder()
        .content(TEST_COMMENT_CONTENT)
        .build();

    Comment TEST_ANOTHER_COMMENT = Comment.builder()
        .content(ANOTHER_PREFIX + TEST_COMMENT_CONTENT)
        .build();
}
