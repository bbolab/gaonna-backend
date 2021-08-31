package com.bbolab.gaonna.api.v1.controller;

import com.bbolab.gaonna.api.AbstractContainerBaseTest;
import com.bbolab.gaonna.api.MockMvcTest;
import com.bbolab.gaonna.api.v1.dto.reserve.ReserveQuestRequestDto;
import com.bbolab.gaonna.api.v1.dto.reserve.ReserveQuestResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcTest
class MockQuestReserveControllerTest extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("[Get] 퀘스트 예약 조회")
    void getQuest() throws Exception {
        String questId = UUID.randomUUID().toString();
        String reserveId = UUID.randomUUID().toString();

        MvcResult result = mockMvc.perform(get(String.format("/v1/reserve/quest/%s/%s", questId, reserveId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        ReserveQuestResponseDto dto = objectMapper.readValue(content, ReserveQuestResponseDto.class);

        assertEquals(dto.getQuestId(), questId);
        assertEquals(dto.getReserveId(), reserveId);
        assertNotNull(dto.getNickname());
        assertNotNull(dto.getProfileId());
    }

    @Test
    @DisplayName("[Post] 퀘스트 예약 요청")
    void reserve() throws Exception {
        String questId = UUID.randomUUID().toString();
        String profileId = UUID.randomUUID().toString();
        ReserveQuestRequestDto requestDto = ReserveQuestRequestDto.builder().profileId(profileId).build();

        MvcResult result = mockMvc.perform(post("/v1/reserve/quest/" + questId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andReturn();

        assertNotNull(result.getResponse().getHeader("Location"));
    }

    @Test
    @DisplayName("[Cancel] 퀘스트 취소")
    void cancel() throws Exception {
        String questId = UUID.randomUUID().toString();
        String reserveId = UUID.randomUUID().toString();
        MvcResult result = mockMvc.perform(delete(String.format("/v1/reserve/quest/%s/%s", questId, reserveId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("[Accept] 퀘스트 수락")
    void accept() throws Exception {
        String reserveId = UUID.randomUUID().toString();
        MvcResult result = mockMvc.perform(post("/v1/reserve/quest/accept/" + reserveId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();
    }
}