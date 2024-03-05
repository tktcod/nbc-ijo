package com.spring.nbcijo.service.contracts;

import com.spring.nbcijo.dto.request.AdminRegisterRequestDto;
import com.spring.nbcijo.dto.response.UserResponseDto;
import java.util.List;

public interface AdminService {

    public void register(AdminRegisterRequestDto requestDto);

    public List<UserResponseDto> getAllUsers();
}
