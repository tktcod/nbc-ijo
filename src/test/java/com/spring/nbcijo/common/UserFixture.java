package com.spring.nbcijo.common;

import com.spring.nbcijo.dto.request.SignupRequestDto;
import com.spring.nbcijo.dto.response.MyInformResponseDto;
import com.spring.nbcijo.entity.User;
import com.spring.nbcijo.entity.UserRoleEnum;

public interface UserFixture {

    String ANOTHER_PREFIX = "another-";
    Long TEST_USER_ID = 1L;

    Long TEST_FAIL_USER_ID = 0L;
    Long TEST_ANOTHER_USER_ID = 2L;
    String TEST_USER_NAME = "username";
    String TEST_USER_PASSWORD = "Dd1@Dd1@";
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

    SignupRequestDto TEST_SIGN_UP_REQUEST_DTO = SignupRequestDto.builder()
        .username(TEST_USER_NAME)
        .password(TEST_USER_PASSWORD)
        .passwordConfirm(TEST_USER_PASSWORD)
        .description(TEST_USER_DESCRIPTION)
        .build();

    MyInformResponseDto TEST_USER_RESPONSE = MyInformResponseDto.builder()
        .id(TEST_USER_ID)
        .username(TEST_USER_NAME)
        .role(UserRoleEnum.USER)
        .description(TEST_USER_DESCRIPTION)
        .build();
}
