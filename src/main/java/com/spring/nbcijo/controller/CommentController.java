package com.spring.nbcijo.controller;

import com.spring.nbcijo.dto.request.CommentRequestDto;
import com.spring.nbcijo.dto.response.ResponseDto;
import com.spring.nbcijo.entity.User;
import com.spring.nbcijo.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments/{postId}")
    public ResponseEntity<ResponseDto<Void>> createComment(
        @PathVariable Long postId,
        @RequestBody @Valid CommentRequestDto requestDto) {
        //인증된 유저
        User user = new User();
        commentService.createComment(user, postId, requestDto);
        return ResponseEntity.status(HttpStatus.OK.value())
            .body(ResponseDto.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .message("댓글 작성 성공")
                .build());
    }
}
