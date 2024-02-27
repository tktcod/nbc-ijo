package com.spring.nbcijo.service;

import com.spring.nbcijo.dto.request.PostRequestDto;
import com.spring.nbcijo.dto.response.PostResponseDto;
import com.spring.nbcijo.entity.Post;
import com.spring.nbcijo.entity.User;
import com.spring.nbcijo.global.enumeration.ErrorCode;
import com.spring.nbcijo.global.exception.InvalidInputException;
import com.spring.nbcijo.repository.PostRepository;
import com.spring.nbcijo.repository.UserRepository;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostResponseDto createPost(PostRequestDto requestDto, User user) {
        user = userRepository.findById(user.getId())
            .orElseThrow(() -> new InvalidInputException(ErrorCode.USER_NOT_FOUND));

        Post post = Post.builder()
            .title(requestDto.getTitle())
            .content(requestDto.getContent())
            .user(user)
            .build();

        return new PostResponseDto(postRepository.save(post));
    }

    public PostResponseDto getPost(Long postId) {
        Post post = findPost(postId);

        return new PostResponseDto(post);
    }

    public List<PostResponseDto> getPostList() {
        List<Post> postList = postRepository.findAll(Sort.by(Direction.DESC, "createdAt"));

        return postList.stream()
            .map(PostResponseDto::new)
            .toList();
    }

    @Transactional
    public void updatePost(Long postId, PostRequestDto requestDto, User user) {
        Post post = findPost(postId);
        validateUser(post.getUser().getId(), user.getId());

        post.update(requestDto);
    }

    private Post findPost(Long postId) {
        return postRepository.findById(postId)
            .orElseThrow(() -> new InvalidInputException(ErrorCode.NOT_FOUND_POST));
    }

    private void validateUser(Long writerId, Long inputId) {
        if (!Objects.equals(writerId, inputId)) {
            throw new InvalidInputException(ErrorCode.NOT_VALID_USER);
        }
    }
}
