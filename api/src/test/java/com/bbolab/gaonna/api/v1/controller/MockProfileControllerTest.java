package com.bbolab.gaonna.api.v1.controller;

import com.bbolab.gaonna.api.AbstractContainerBaseTest;
import com.bbolab.gaonna.api.MockMvcTest;
import com.bbolab.gaonna.api.v1.dto.member.profile.ProfileCreateRequestDto;
import com.bbolab.gaonna.api.v1.dto.member.profile.ProfileDetailDto;
import com.bbolab.gaonna.api.v1.dto.member.profile.ProfileDetailListDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcTest
class MockProfileControllerTest  extends AbstractContainerBaseTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("[Get] 프로필 조회")
    void getProfile() throws Exception {
        String profileId = UUID.randomUUID().toString();

        MvcResult result = mockMvc.perform(get("/v1/profile/" + profileId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        ProfileDetailDto dto = objectMapper.readValue(content, ProfileDetailDto.class);

        assertEquals(profileId, dto.getProfileId());
        assertNotNull(dto.getProfileName());
        assertNotNull(dto.getProfileImageId());
        assertNotNull(dto.getDescription());
        assertNotNull(dto.getMember());
        assertEquals(dto.getProfileQuestCount(), dto.getProfileQuests().size());
        assertNotNull(dto.getScore());
        assertEquals(dto.getReviewCount(), dto.getReviews().size());
    }

    @Test
    @DisplayName("[Get] 프로필 모두 조회")
    void getAllProfile() throws Exception {
        MvcResult result = mockMvc.perform(get("/v1/profile/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        ProfileDetailListDto dto = objectMapper.readValue(content, ProfileDetailListDto.class);
        assertEquals(dto.getProfileCount(), 2);
        assertEquals(dto.getProfileCount(), dto.getProfiles().size());
    }

    @Test
    @DisplayName("[Post] 프로필 생성")
    void postProfile() throws Exception {
        ProfileCreateRequestDto requestDto = ProfileCreateRequestDto.builder()
                .profileName("testName")
                .profileImageId(UUID.randomUUID().toString())
                .description("testDescription")
                .build();

        MvcResult result = mockMvc.perform(post("/v1/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andReturn();

        assertNotNull(result.getResponse().getHeader("Location"));
    }

    @Test
    @DisplayName("[Put] 프로필 수정")
    void putProfile() throws Exception{
        String profileId = UUID.randomUUID().toString();
        ProfileCreateRequestDto requestDto = ProfileCreateRequestDto.builder()
                .profileName("testName")
                .profileImageId(UUID.randomUUID().toString())
                .description("testDescription")
                .build();

        MvcResult result = mockMvc.perform(put("/v1/profile/" + profileId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("[Delete] 프로필 삭제")
    void deleteProfile() throws Exception {
        String profileId = UUID.randomUUID().toString();

        mockMvc.perform(delete("/v1/profile/" + profileId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();
    }
}