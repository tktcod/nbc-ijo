package com.spring.nbcijo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDescriptionRequestDto {

    private String password;
    private String updateDescription;
}
