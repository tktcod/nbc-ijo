package com.spring.nbcijo.controller;

import com.spring.nbcijo.dto.request.CommentRequestDto;
import com.spring.nbcijo.dto.response.CommentResponseDto;
import com.spring.nbcijo.dto.response.ResponseDto;
import com.spring.nbcijo.entity.Post;
import com.spring.nbcijo.entity.User;
import com.spring.nbcijo.entity.UserRoleEnum;
import com.spring.nbcijo.repository.PostRepository;
import com.spring.nbcijo.repository.UserRepository;
import com.spring.nbcijo.service.CommentService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<ResponseDto<Void>> createComment(
        @PathVariable Long postId,
        @RequestBody @Valid CommentRequestDto requestDto) {
        //인증된 유저
        User user = User.builder().username("username").password("passworrd")
            .role(UserRoleEnum.USER)
            .build();
        Post testPost = Post.builder().title("title").content("content").build();
        userRepository.save(user);
        postRepository.save(testPost);

        commentService.createComment(user, postId, requestDto);
        return ResponseEntity.status(HttpStatus.OK.value())
            .body(ResponseDto.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .message("댓글 작성 성공")
                .build());
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<ResponseDto<List<CommentResponseDto>>> getComments(
        @PathVariable Long postId) {
        List<CommentResponseDto> dtos = commentService.getComments(postId);
        return ResponseEntity.status(HttpStatus.OK.value())
            .body(ResponseDto.<List<CommentResponseDto>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("댓글 조회 성공")
                .data(dtos)
                .build());
    }

    @PatchMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<ResponseDto<Void>> updateComment(
        @PathVariable Long postId,
        @RequestBody CommentRequestDto requestDto) {
        //인증된 유저
        User user = User.builder().username("username").password("passworrd")
            .role(UserRoleEnum.USER)
            .build();
        commentService.updateComment(user, postId, requestDto);
        return ResponseEntity.status(HttpStatus.OK.value())
            .body(ResponseDto.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .message("댓글 수정 성공")
                .build());
    }
}
