package com.spring.nbcijo.post;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.spring.nbcijo.common.ControllerTest;
import com.spring.nbcijo.common.PostFixture;
import com.spring.nbcijo.common.UserFixture;
import com.spring.nbcijo.controller.PostController;
import com.spring.nbcijo.dto.request.PostRequestDto;
import com.spring.nbcijo.service.PostService;
import org.junit.jupiter.api.DisplayName;
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
}
