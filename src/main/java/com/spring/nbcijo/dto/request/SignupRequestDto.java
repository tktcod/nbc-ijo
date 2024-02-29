package com.spring.nbcijo.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class SignupRequestDto {

    @NotNull
    @Pattern(regexp = "^[a-z0-9]{4,10}$")
    private String username;

    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*-])[A-Za-z\\d!@#$%^&*-]{8,15}$")
    // 알파벳 대문자, 소문자, 숫자, 특수문자 각각 1개 이상 필요
    private String password;

    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*-])[A-Za-z\\d!@#$%^&*-]{8,15}$")
    private String passwordConfirm;

    private String description;
}
