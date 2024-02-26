package com.spring.nbcijo.mypage;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.spring.nbcijo.common.UserFixture;
import com.spring.nbcijo.controller.MyPageController;
import com.spring.nbcijo.service.MyPageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MyPageController.class)
@MockBean(JpaMetamodelMappingContext.class)
class MyPageControllerTest implements UserFixture {

    @MockBean
    MyPageService myPageService;

    @Autowired
    MockMvc mockMvc;

    @Nested
    @DisplayName("마이페이지 내 정보 조회")
    class getMyInform {

        @Test
        @DisplayName("내 정보 조회 성공")
        void getMyInform_success() throws Exception {
            // given
            given(myPageService.getMyInform(any())).willReturn(TEST_USER_RESPONSE);

            // when
            var action = mockMvc.perform(get("/my"));

            action.andExpect(status().isOk())
                .andDo(print());
        }
    }
}
