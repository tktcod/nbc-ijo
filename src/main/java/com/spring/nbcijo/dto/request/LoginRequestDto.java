package com.spring.nbcijo.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {

    @NotNull
    private String username;
    @NotNull
    private String password;
}
