package com.spring.nbcijo.service.contracts;

import com.spring.nbcijo.dto.request.PostRequestDto;
import com.spring.nbcijo.dto.response.PostResponseDto;
import com.spring.nbcijo.entity.User;
import java.util.List;

public interface PostService {

    public PostResponseDto createPost(PostRequestDto requestDto, User user);

    public PostResponseDto getPost(Long postId);

    public List<PostResponseDto> getPostList();

    public void updatePost(Long postId, PostRequestDto requestDto, User user);

    public void deletePost(Long postId, User user);
}
