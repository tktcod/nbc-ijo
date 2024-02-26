package com.spring.nbcijo.common;

import com.spring.nbcijo.entity.User;
import com.spring.nbcijo.entity.UserRoleEnum;

public interface UserFixture {

    String ANOTHER_PREFIX = "another-";
    Long TEST_USER_ID = 1L;
    Long TEST_ANOTHER_USER_ID = 2L;
    String TEST_USER_NAME = "username";
    String TEST_USER_PASSWORD = "password";
    String TEST_USER_DESCRIPTION = "default message";

    User TEST_USER = User.builder()
        .username(TEST_USER_NAME)
        .password(TEST_USER_PASSWORD)
        .description(TEST_USER_DESCRIPTION)
        .role(UserRoleEnum.USER)
        .build();

    User TEST_ANOTHER_USER = User.builder()
        .username(ANOTHER_PREFIX + TEST_USER_NAME)
        .password(ANOTHER_PREFIX + TEST_USER_PASSWORD)
        .description(TEST_USER_DESCRIPTION)
        .build();
}
