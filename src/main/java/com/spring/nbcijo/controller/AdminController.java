package com.spring.nbcijo.controller;

import com.spring.nbcijo.dto.request.AdminRegisterRequestDto;
import com.spring.nbcijo.dto.response.ResponseDto;
import com.spring.nbcijo.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/admin/register")
    public ResponseEntity<ResponseDto<Void>> registerAdmin(
        @RequestBody @Valid AdminRegisterRequestDto requestDto) {
        adminService.register(requestDto);
        return ResponseEntity.status(HttpStatus.OK.value())
            .body(ResponseDto.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .message("관리자 등록 성공")
                .build());
    }
}
