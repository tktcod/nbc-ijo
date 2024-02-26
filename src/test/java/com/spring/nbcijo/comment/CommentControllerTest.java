package com.spring.nbcijo.comment;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;

import com.spring.nbcijo.common.CommentFixture;
import com.spring.nbcijo.common.ControllerTest;
import com.spring.nbcijo.common.PostFixture;
import com.spring.nbcijo.common.UserFixture;
import com.spring.nbcijo.dto.request.CommentRequestDto;
import com.spring.nbcijo.entity.User;
import com.spring.nbcijo.global.enumeration.ErrorCode;
import com.spring.nbcijo.global.exception.InvalidInputException;
import com.spring.nbcijo.global.exception.NbcIjoException;
import com.spring.nbcijo.service.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

public class CommentControllerTest extends ControllerTest implements CommentFixture, PostFixture,
    UserFixture {

    @MockBean
    private CommentService commentService;

    @Nested
    @DisplayName("댓글 생성 요청")
    class createComment {

        @DisplayName("댓글 생성 요청 성공")
        @Test
        void createComment_success() throws Exception {
            //given //when
            var action = mockMvc.perform(post("/comments/{postId}", TEST_POST_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TEST_COMMENT_REQUEST_DTO)));

            //then
            action.andExpect(status().isOk());
            verify(commentService, times(1))
                .createComment(any(User.class), eq(TEST_POST_ID), any(CommentRequestDto.class));
        }

        @DisplayName("댓글 생성 요청 실패 - 존재하지 않는 게시글 id")
        @Test
        void createComment_fail() throws Exception {
            //given
            doThrow(new InvalidInputException(ErrorCode.NOT_FOUND_POST)).when(commentService)
                .createComment(any(User.class), eq(TEST_POST_ID), any(CommentRequestDto.class));

            //when
            var action = mockMvc.perform(post("/comments/{postId}", TEST_POST_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TEST_COMMENT_REQUEST_DTO)));

            //then
            action
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                    .value(ErrorCode.NOT_FOUND_POST.getMessage()));
        }
    }
}
