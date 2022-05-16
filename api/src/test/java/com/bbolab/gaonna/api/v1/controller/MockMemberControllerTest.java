package com.bbolab.gaonna.api.v1.controller;

import com.bbolab.gaonna.api.AbstractContainerBaseTest;
import com.bbolab.gaonna.api.MockMvcTest;
import com.bbolab.gaonna.api.WithAccount;
import com.bbolab.gaonna.api.v1.dto.member.MemberInfoDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockMvcTest
class MockMemberControllerTest extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("[Get] 사용자 정보 조회")
    @WithAccount("bbobbi") 
    void getUserInfo() throws Exception {
        String memberId = UUID.randomUUID().toString();
        MvcResult result = mockMvc.perform(get("/v1/member/info/" + memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        MemberInfoDto infoDto = objectMapper.readValue(content, MemberInfoDto.class);
        assertEquals(memberId, infoDto.getMemberId());
        assertNotNull(infoDto.getNickname());
        assertEquals(infoDto.getRequestedQuestCount(), infoDto.getRequestedQuest().size());
        assertEquals(infoDto.getPerformedQuestCount(), infoDto.getPerformedQuest().size());
        assertEquals(infoDto.getProfilesCount(), infoDto.getProfiles().size());
        assertEquals(infoDto.getReviewCount(), infoDto.getReviews().size());
    }

}