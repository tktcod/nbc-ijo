package com.spring.nbcijo.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminLoginRequestDto {

    @NotBlank
    private String adminName;
    @NotBlank
    private String password;
}

