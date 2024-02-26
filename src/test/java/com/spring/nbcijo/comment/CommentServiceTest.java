package com.spring.nbcijo.comment;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.*;

import com.spring.nbcijo.common.CommentFixture;
import com.spring.nbcijo.common.PostFixture;
import com.spring.nbcijo.entity.Comment;
import com.spring.nbcijo.entity.Post;
import com.spring.nbcijo.repository.CommentRepository;
import com.spring.nbcijo.repository.PostRepository;
import com.spring.nbcijo.service.CommentService;
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
}
