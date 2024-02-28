package com.spring.nbcijo.post;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.spring.nbcijo.common.ControllerTest;
import com.spring.nbcijo.common.PostFixture;
import com.spring.nbcijo.common.UserFixture;
import com.spring.nbcijo.controller.PostController;
import com.spring.nbcijo.dto.request.PostRequestDto;
import com.spring.nbcijo.global.enumeration.ErrorCode;
import com.spring.nbcijo.global.exception.InvalidInputException;
import com.spring.nbcijo.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

@WebMvcTest(PostController.class)
class PostControllerTest extends ControllerTest implements PostFixture, UserFixture {

    @MockBean
    private PostService postService;

    @DisplayName("게시글 생성 요청")
    @Test
    void createdPost_success() throws Exception {
        // given // when
        var action = mockMvc.perform(post("/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(TEST_POST_REQUEST_DTO)));

        // then
        action.andExpect(status().isCreated());
        verify(postService, times(1))
            .createPost(any(PostRequestDto.class), eq(TEST_USER));
    }

    @Nested
    @DisplayName("게시글 조회 요청")
    class getPost {

        @DisplayName("게시글 조회 요청 성공")
        @Test
        void getPost_success() throws Exception {
            // given
            given(postService.getPost(eq(TEST_POST_ID))).willReturn(TEST_POST_RESPONSE_DTO);

            // when
            var action = mockMvc.perform(get("/posts/{postId}", TEST_POST_ID)
                .accept(MediaType.APPLICATION_JSON));

            // then
            action
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value(TEST_POST_TITLE))
                .andExpect(jsonPath("$.data.content").value(TEST_POST_CONTENT));
        }

        @DisplayName("게시글 조회 요청 실패 - 존재하지 않는 게시글ID")
        @Test
        void getPost_fail_postIdNotExist() throws Exception {
            // given
            given(postService.getPost(eq(TEST_POST_ID))).willThrow(
                new InvalidInputException(ErrorCode.NOT_FOUND_POST));

            // when
            var action = mockMvc.perform(get("/posts/{postId}", TEST_POST_ID)
                .accept(MediaType.APPLICATION_JSON));

            // then
            action
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                .value(ErrorCode.NOT_FOUND_POST.getMessage()));
        }
    }
}
