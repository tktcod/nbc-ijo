package com.spring.nbcijo.comment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;

import com.spring.nbcijo.common.CommentFixture;
import com.spring.nbcijo.common.PostFixture;
import com.spring.nbcijo.dto.response.CommentResponseDto;
import com.spring.nbcijo.entity.Comment;
import com.spring.nbcijo.repository.CommentRepository;
import com.spring.nbcijo.repository.PostRepository;
import com.spring.nbcijo.service.CommentService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest implements CommentFixture, PostFixture {

    @InjectMocks
    CommentService commentService;

    @Mock
    CommentRepository commentRepository;

    @Mock
    PostRepository postRepository;

    @DisplayName("댓글 생성")
    @Test
    void createComment() {
        //given
        given(postRepository.findById(eq(TEST_POST_ID))).willReturn(Optional.of(TEST_POST));

        //when & then
        assertDoesNotThrow(() -> commentService
            .createComment(TEST_USER, TEST_POST_ID, TEST_COMMENT_REQUEST_DTO));
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @DisplayName("댓글 조회")
    @Test
    void getComments() {
        //given
        var testComment =
            CommentTestUtils.get(TEST_COMMENT, 1L, LocalDateTime.now(), TEST_USER, TEST_POST);
        var testComment2 =
            CommentTestUtils.get(TEST_ANOTHER_COMMENT, 2L, LocalDateTime.now().minusMinutes(1),
                TEST_USER, TEST_POST);
        given(postRepository.findById(eq(TEST_POST_ID))).willReturn(Optional.of(TEST_POST));
        given(commentRepository.findAllByPostIdOrderByCreatedAtDesc(TEST_POST_ID))
            .willReturn(List.of(testComment, testComment2));

        //when
        var result = commentService.getComments(TEST_POST_ID);

        //then
        assertThat(result).hasSize(2);
        assertThat(result.get(0)).isEqualTo(new CommentResponseDto(testComment));
        assertThat(result.get(1)).isEqualTo(new CommentResponseDto(testComment2));
    }
}
