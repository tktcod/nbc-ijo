package com.spring.nbcijo.dto.response;

import com.spring.nbcijo.entity.UserRoleEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyInformResponseDto {

    private Long id;
    private String username;
    private UserRoleEnum role;
    private String description;

    @Builder
    public MyInformResponseDto(Long id, String username, UserRoleEnum role, String description) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.description = description;
    }
}
