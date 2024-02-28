package com.spring.nbcijo.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class LoginRequestDto {

    @NotNull
    private String username;
    @NotNull
    private String password;
}
