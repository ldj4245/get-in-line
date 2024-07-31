package com.uno.getinline.integration;


import com.uno.getinline.constant.ErrorCode;
import com.uno.getinline.constant.EventStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class APIEventIntergrationTest {


    @Autowired private MockMvc mvc;


    @Test
    void asdfasd() throws Exception{
        //Given



        //when & Then
        mvc.perform(
                get("/api/events")
                        .queryParam("placeId","1")
                        .queryParam("eventName","운동")
                        .queryParam("eventStatus", EventStatus.OPENED.name())
                        .queryParam("eventStartDatetime","2021-01-01T00:00:00")
                        .queryParam("eventEndDatetime","2021-01-02T00:00:00")
        )

                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()))
                .andDo(print());


    }

}
