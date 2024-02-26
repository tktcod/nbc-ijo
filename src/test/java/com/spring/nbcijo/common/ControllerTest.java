package com.spring.nbcijo.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.nbcijo.controller.CommentController;
import com.spring.nbcijo.controller.MyPageController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {CommentController.class, MyPageController.class})
@MockBean(JpaMetamodelMappingContext.class)
public class ControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;
}
