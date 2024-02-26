package com.spring.nbcijo.comment;

import static org.mockito.BDDMockito.*;

import com.spring.nbcijo.common.CommentFixture;
import com.spring.nbcijo.common.PostFixture;
import com.spring.nbcijo.repository.CommentRepository;
import com.spring.nbcijo.repository.PostRepository;
import com.spring.nbcijo.service.CommentService;
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
        //when
        //then
    }
}
