package com.spring.nbcijo.service.contracts;

import com.spring.nbcijo.dto.request.SignupRequestDto;

public interface UserService {

    public void signup(SignupRequestDto requestDto);

    public void logout(String refreshToken);
}
