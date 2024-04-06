package com.example.ria.controller;


import com.example.ria.RiaApplication;
import com.example.ria.common.Constants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RiaApplication.class)
@AutoConfigureMockMvc
@DisplayName("한국 관광 공사 api test")
class PublicDataMapControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("위치기반장소 검색")
    void searchByKeywordTest() throws Exception {

        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute(Constants.SESSION_USER_ID, "USER00");

        // 테스트에 필요한 입력값 설정
        String mapX = "126.923885";
        String mapY = "37.557472";
        String radius = "1000";

        this.mvc.perform(get("/api/searchPlace")
                        .param("mapX", mapX)
                        .param("mapY", mapY)
                        .param("radius", radius)
                        .param("userId", Constants.SESSION_USER_ID)
                        .session(mockHttpSession))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print(System.out));


    }


}