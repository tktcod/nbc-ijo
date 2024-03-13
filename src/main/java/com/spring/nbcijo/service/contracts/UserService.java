package com.spring.nbcijo.service.contracts;

import com.spring.nbcijo.dto.request.SignupRequestDto;
import org.springframework.security.core.Authentication;

public interface UserService {

    public void signup(SignupRequestDto requestDto);

    public void logout(String refreshToken);

    public Authentication login(String username, String password);
}
