package com.spring.nbcijo.controller;

import com.spring.nbcijo.dto.request.UpdateDescriptionRequestDto;
import com.spring.nbcijo.dto.response.MyInformResponseDto;
import com.spring.nbcijo.dto.response.ResponseDto;
import com.spring.nbcijo.security.UserDetailsImpl;
import com.spring.nbcijo.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/my")
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping
    public ResponseEntity<ResponseDto<MyInformResponseDto>> getMyInform(@AuthenticationPrincipal
    UserDetailsImpl userDetails) {
        MyInformResponseDto myInformResponseDto = myPageService.getMyInform(userDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK)
            .body(ResponseDto.<MyInformResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("내 정보 조회가 완료되었습니다.")
                .data(myInformResponseDto).build());
    }

    @PutMapping
    public ResponseEntity<Void> updateMyDependency(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody UpdateDescriptionRequestDto updateDescriptionRequestDto) {
        myPageService.updateMyDependency(userDetails.getUser(), updateDescriptionRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
