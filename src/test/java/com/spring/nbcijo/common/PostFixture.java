package com.spring.nbcijo.common;

import com.spring.nbcijo.entity.Post;

public interface PostFixture extends UserFixture{

    Long TEST_POST_ID = 1L;
    String TEST_POST_TITLE = "title";
    String TEST_POST_CONTENT = "content";

    Post TEST_POST = Post.builder()
        .title(TEST_POST_TITLE)
        .content(TEST_POST_CONTENT)
        .build();

    Post TEST_ANOTHER_TODO = Post.builder()
        .title(ANOTHER_PREFIX + TEST_POST_TITLE)
        .content(ANOTHER_PREFIX + TEST_POST_CONTENT)
        .build();
}
