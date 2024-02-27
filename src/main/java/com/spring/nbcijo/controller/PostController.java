package com.spring.nbcijo.controller;

import com.spring.nbcijo.dto.request.PostRequestDto;
import com.spring.nbcijo.dto.response.PostResponseDto;
import com.spring.nbcijo.dto.response.ResponseDto;
import com.spring.nbcijo.security.UserDetailsImpl;
import com.spring.nbcijo.service.PostService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<ResponseDto<PostResponseDto>> createPost(@Valid @RequestBody
    PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PostResponseDto responseDto = postService.createPost(requestDto, userDetails.getUser());

        return ResponseEntity.status(HttpStatus.CREATED.value()).body(
            ResponseDto.<PostResponseDto>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("게시물 작성 성공")
                .data(responseDto)
                .build());
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ResponseDto<PostResponseDto>> getPost(@PathVariable Long postId) {
        PostResponseDto responseDto = postService.getPost(postId);

        return ResponseEntity.status(HttpStatus.OK.value())
            .body(ResponseDto.<PostResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("게시글 조회 성공")
                .data(responseDto)
                .build());
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<PostResponseDto>>> getPostList() {
        List<PostResponseDto> response = postService.getPostList();

        return ResponseEntity.status(HttpStatus.OK.value())
            .body(ResponseDto.<List<PostResponseDto>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("게시글 전체 조회 성공")
                .data(response)
                .build());
    }

    @PutMapping("/{postId}")
    public ResponseEntity<ResponseDto<Void>> updatePost(@PathVariable Long postId,
        @RequestBody PostRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.updatePost(postId, requestDto, userDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK.value())
            .body(ResponseDto.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .message("게시글 수정 성공")
                .build());
    }
}
