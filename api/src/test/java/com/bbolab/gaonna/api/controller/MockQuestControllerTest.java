package com.bbolab.gaonna.api.controller;

import com.bbolab.gaonna.api.AbstractContainerBaseTest;
import com.bbolab.gaonna.api.MockMvcTest;
import com.bbolab.gaonna.api.v1.controller.validator.BboxConstraintValidator;
import com.bbolab.gaonna.api.v1.dto.category.CategoryDto;
import com.bbolab.gaonna.api.v1.dto.quest.QuestCreateUpdateRequestDto;
import com.bbolab.gaonna.api.v1.dto.quest.QuestDetailResponseDto;
import com.bbolab.gaonna.api.v1.dto.quest.QuestListResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@MockMvcTest
public class MockQuestControllerTest extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("[Create] 퀘스트 생성 - 성공")
    void createQuestSuccess() throws Exception {
        // given
        QuestCreateUpdateRequestDto requestDto = createDummyQuestRequestDto();
        // when
        MvcResult mvcResult = mockMvc.perform(post("/v1/quest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))
                .with(csrf()))
                .andExpect(status().isCreated())
                .andReturn();

        // then
        String content = mvcResult.getResponse().getContentAsString();
        QuestDetailResponseDto responseDto = objectMapper.readValue(content, QuestDetailResponseDto.class);

        assertEquals(responseDto.getTitle(), requestDto.getTitle());
        assertEquals(responseDto.getContent(), requestDto.getContent());
        assertEquals(responseDto.getLatitude(), requestDto.getLatitude());
        assertEquals(responseDto.getLongitude(), requestDto.getLongitude());
        assertEquals(responseDto.getPrice(), requestDto.getPrice());
        assertEquals(responseDto.getDeadline(), requestDto.getDeadline());
        assertEquals(requestDto.getTags(), responseDto.getTags());
        assertEquals(requestDto.getCategories(), responseDto.getCategories());
    }

    @Test
    @DisplayName("[Read] 단일 퀘스트 조회 - 성공")
    public void readQuestTest() throws Exception {
        // given
        String uuid = UUID.randomUUID().toString();

        // when
        MvcResult result = mockMvc.perform(get(String.format("/v1/quest/%s", uuid))
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        // then
        String content = result.getResponse().getContentAsString();
        QuestDetailResponseDto dto = objectMapper.readValue(content, QuestDetailResponseDto.class);

        assertEquals(dto.getQuestId(), uuid);
    }

    @Test
    @DisplayName("[Read] 리스트 퀘스트 조회 - 성공")
    public void readQuestListTest() throws Exception {
        // given
        Double topLatitude = 37.2796836;
        Double topLongitude = 127.04645333;
        Double bottomLatitude = 37.2746168;
        Double bottomLongitude = 127.0403165;

        // when
        MvcResult result = mockMvc.perform(get(
                String.format("/v1/quest?bbox=[[%f,%f],[%f,%f]]",
                        bottomLongitude, bottomLatitude, topLongitude, topLatitude))
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();
        // then
        String content = result.getResponse().getContentAsString();
        QuestListResponseDto dto = objectMapper.readValue(content, QuestListResponseDto.class);
        assertEquals(dto.getQuests().size(), 1);
        assertNotNull(dto.getQuests().get(0).getLongitude());
        assertNotNull(dto.getQuests().get(0).getLatitude());
    }

    @Test
    @DisplayName("[Read] 리스트 퀘스트 조회 - 실패 (wrong format)")
    public void readQuestListFailTest() throws Exception {
        // given
        Double topLatitude = 37.2796836;
        Double topLongitude = 127.04645333;
        Double bottomLatitude = 37.2746168;
        Double bottomLongitude = 127.0403165;

        // when
        MvcResult result = mockMvc.perform(get(
                        String.format("/v1/quest?bbox=[[%f,%f],[%f,%f]",
                                bottomLongitude, bottomLatitude, topLongitude, topLatitude))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals(content, BboxConstraintValidator.wrongFormatExceptionMsg);
    }

    @Test
    @DisplayName("[Read] 리스트 퀘스트 조회 - 실패 (bbox wrong range)")
    public void readQuestListFailTest2() throws Exception {
        // given
        Double topLatitude = 37.2796836;
        Double topLongitude = 127.04645333;
        Double bottomLatitude = 32.2746168;
        Double bottomLongitude = 127.0403165;

        // when
        MvcResult result = mockMvc.perform(get(
                        String.format("/v1/quest?bbox=[[%f,%f],[%f,%f]]",
                                bottomLongitude, bottomLatitude, topLongitude, topLatitude))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals(content, BboxConstraintValidator.wrongBboxRangeExceptionMsg);
    }

    @Test
    @DisplayName("[Read] 리스트 퀘스트 조회 - 실패 (bbox 좌하단, 우상단 조건 불만족")
    public void readQuestListFailTest3() throws Exception {
        Double bottomLatitude = 37.2796836;
        Double bottomLongitude = 127.04645333;
        Double topLatitude = 37.2746168;
        Double topLongitude = 127.0403165;
        // when
        MvcResult result = mockMvc.perform(get(
                        String.format("/v1/quest?bbox=[[%f,%f],[%f,%f]]",
                                bottomLongitude, bottomLatitude, topLongitude, topLatitude))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals(content, BboxConstraintValidator.wrongCoordinatesExceptionMsg);
    }

    @Test
    @DisplayName("[Update] 퀘스트 수정 - 성공")
    public void updateQuestTest() throws Exception {
        // given
        String questId = UUID.randomUUID().toString();
        QuestCreateUpdateRequestDto requestDto = createDummyQuestRequestDto();

        // when
        MvcResult result = mockMvc.perform(put("/v1/quest/" + questId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        // then
        String content = result.getResponse().getContentAsString();
        QuestDetailResponseDto responseDto = objectMapper.readValue(content, QuestDetailResponseDto.class);

        assertEquals(responseDto.getQuestId(), questId);
        assertEquals(responseDto.getTitle(), requestDto.getTitle());
        assertEquals(responseDto.getContent(), requestDto.getContent());
        assertEquals(responseDto.getLatitude(), requestDto.getLatitude());
        assertEquals(responseDto.getLongitude(), requestDto.getLongitude());
        assertEquals(responseDto.getPrice(), requestDto.getPrice());
        assertEquals(responseDto.getDeadline(), requestDto.getDeadline());
        assertEquals(requestDto.getTags(), responseDto.getTags());
        assertEquals(requestDto.getCategories(), responseDto.getCategories());
    }

    @Test
    @DisplayName("[Delete] 퀘스트 삭제 - 성공")
    public void deleteQuestTest() throws Exception {
        // given
        String uuid = UUID.randomUUID().toString();

        // when
        MvcResult result = mockMvc.perform(delete(String.format("/v1/quest/%s", uuid))
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        // then
        assertEquals(result.getResponse().getStatus(), HttpServletResponse.SC_OK);
    }

    // TODO : Validator test

    public static QuestCreateUpdateRequestDto createDummyQuestRequestDto() {
        return QuestCreateUpdateRequestDto.builder()
                .title("test-title")
                .content("test-content")
                .latitude(35.332211)
                .longitude(121.112233)
                .price(10000)
                .deadline(LocalDateTime.now())
                .tags((Arrays.asList(new String[]{"tag1", "tag2"}.clone())))
                .categories(Collections.singletonList(CategoryDto.builder().key("key").value("value").build()))
                .build();
    }
}
