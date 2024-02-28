package com.spring.nbcijo.common;

import com.spring.nbcijo.dto.request.UpdateDescriptionRequestDto;
import com.spring.nbcijo.dto.request.UpdatePasswordRequestDto;
import com.spring.nbcijo.dto.response.MyInformResponseDto;
import com.spring.nbcijo.entity.PasswordHistory;
import com.spring.nbcijo.entity.User;
import com.spring.nbcijo.entity.UserRoleEnum;
import java.time.LocalDateTime;

public interface UserFixture {

    String ANOTHER_PREFIX = "another-";
    String REPOSITORY_PREFIX = "repository-";
    Long TEST_USER_ID = 1L;

    Long TEST_FAIL_USER_ID = 0L;
    Long TEST_ANOTHER_USER_ID = 2L;
    String TEST_USER_NAME = "username";
    String TEST_USER_PASSWORD = "password";
    String TEST_USER_DESCRIPTION = "default message";
    String TEST_USER_UPDATE_DESCRIPTION = "update message";
    String TEST_NEW_PASSWORD = "newPw123!";
    String TEST_NEW_PASSWORD_FAIL = "newPw123";

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

    User TEST_REPOSITORY_USER = User.builder()
        .username(REPOSITORY_PREFIX + TEST_USER_NAME)
        .password(REPOSITORY_PREFIX + TEST_USER_PASSWORD)
        .role(UserRoleEnum.USER)
        .description(REPOSITORY_PREFIX + TEST_USER_DESCRIPTION)
        .build();

    PasswordHistory PASSWORD_HISTORY1 = PasswordHistory.builder()
        .createdAt(LocalDateTime.now())
        .user(TEST_REPOSITORY_USER)
        .password(TEST_REPOSITORY_USER.getPassword())
        .build();

    PasswordHistory PASSWORD_HISTORY2 = PasswordHistory.builder()
        .createdAt(LocalDateTime.now().plusHours(2))
        .user(TEST_REPOSITORY_USER)
        .password(REPOSITORY_PREFIX + TEST_REPOSITORY_USER.getPassword())
        .build();

    PasswordHistory PASSWORD_HISTORY3 = PasswordHistory.builder()
        .createdAt(LocalDateTime.now().plusHours(3))
        .user(TEST_REPOSITORY_USER)
        .password(TEST_NEW_PASSWORD)
        .build();

    MyInformResponseDto TEST_USER_RESPONSE = MyInformResponseDto.builder()
        .id(TEST_USER_ID)
        .username(TEST_USER_NAME)
        .role(UserRoleEnum.USER)
        .description(TEST_USER_DESCRIPTION)
        .build();

    UpdateDescriptionRequestDto TEST_DESCRIPTION_UPDATE_REQUEST = new UpdateDescriptionRequestDto(
        TEST_USER.getPassword(), TEST_USER_UPDATE_DESCRIPTION);

    UpdatePasswordRequestDto TEST_PASSWORD_UPDATE_REQUEST = new UpdatePasswordRequestDto(
        TEST_USER_PASSWORD, TEST_NEW_PASSWORD);

    UpdatePasswordRequestDto TEST_PASSWORD_UPDATE_REQUEST_FAIL = new UpdatePasswordRequestDto(
        TEST_USER_PASSWORD, TEST_NEW_PASSWORD_FAIL);
}
