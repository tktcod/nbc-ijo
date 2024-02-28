package com.spring.nbcijo.dto.response;

import com.spring.nbcijo.entity.User;
import com.spring.nbcijo.entity.UserRoleEnum;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private final String username;
    private final String description;
    private final UserRoleEnum role;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public UserResponseDto(User user) {
        this.username = user.getUsername();
        this.description = user.getDescription();
        this.role = user.getRole();
        this.createdAt = user.getCreatedAt();
        this.modifiedAt = user.getModifiedAt();
    }
}
