package com.spring.nbcijo.post;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;

import com.spring.nbcijo.common.PostFixture;
import com.spring.nbcijo.dto.response.PostResponseDto;
import com.spring.nbcijo.entity.Post;
import com.spring.nbcijo.repository.PostRepository;
import com.spring.nbcijo.repository.UserRepository;
import com.spring.nbcijo.service.PostService;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PostServiceTest implements PostFixture {

    @InjectMocks
    PostService postService;

    @Mock
    PostRepository postRepository;

    @Mock
    UserRepository userRepository;

    @DisplayName("게시글 생성")
    @Test
    void createPost() {
        // given
        given(userRepository.findById(any())).willReturn(Optional.of(TEST_USER));
        given(postRepository.save(any(Post.class))).willReturn(TEST_POST);

        // when & then
        assertDoesNotThrow(() -> postService
            .createPost(TEST_POST_REQUEST_DTO, TEST_USER));
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @DisplayName("게시물 조회")
    @Test
    void getPost() {
        // given
        var testPost = PostTestUtils.get(TEST_POST, TEST_USER);
        given(postRepository.findById(TEST_POST_ID)).willReturn(Optional.of(testPost));

        // when
        var result = postService.getPost(TEST_POST_ID);

        // then
        assertThat(result).isEqualTo(new PostResponseDto(testPost));
    }
}
