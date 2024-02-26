package com.spring.nbcijo.service;

import com.spring.nbcijo.dto.request.CommentRequestDto;
import com.spring.nbcijo.entity.Comment;
import com.spring.nbcijo.entity.Post;
import com.spring.nbcijo.entity.User;
import com.spring.nbcijo.global.enumeration.ErrorCode;
import com.spring.nbcijo.global.exception.NbcIjoException;
import com.spring.nbcijo.repository.CommentRepository;
import com.spring.nbcijo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public void createTodo(User user, Long postId, CommentRequestDto requestDto) {
        Post post = findPost(postId);

        Comment comment = Comment.builder()
            .post(post)
            .user(user)
            .content(requestDto.getContent())
            .build();
        commentRepository.save(comment);
    }

    private Post findPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(
            () -> new NbcIjoException(ErrorCode.NOT_FOUND_POST)
        );
    }
}
