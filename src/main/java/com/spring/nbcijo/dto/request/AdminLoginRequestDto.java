package com.spring.nbcijo.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AdminLoginRequestDto {

    @NotNull
    private String adminName;
    @NotNull
    private String password;
}
