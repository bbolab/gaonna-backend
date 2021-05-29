package com.bbolab.gaonna.api.controller;

import com.bbolab.gaonna.api.MockMvcTest;
import com.bbolab.gaonna.api.v1.dto.article.ArticleCreateRequestDto;
import com.bbolab.gaonna.api.v1.dto.article.ArticleResponseDto;
import com.bbolab.gaonna.api.v1.dto.article.ArticleUpdateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@MockMvcTest
public class MockArticleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("[Create] 게시물 생성 - 성공")
    void createArticleSuccess() throws Exception {
        // given
        ArticleCreateRequestDto requestDto = createDummyArticleCreateQuestDto();
        // when
        MvcResult result = mockMvc.perform(post("/v1/article")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))
                .with(csrf()))
                .andExpect(status().isCreated())
                .andReturn();

        // then
        String content = result.getResponse().getContentAsString();
        ArticleResponseDto dto = objectMapper.readValue(content, ArticleResponseDto.class);
        assertEquals(dto.getTitle(), requestDto.getTitle());
        assertEquals(dto.getContent(), requestDto.getContent());
    }

    @Test
    @DisplayName("[Read] 게시물 조회 - 성공")
    void readArticleSuccess() throws Exception {
        // given
        String articleId = UUID.randomUUID().toString();

        // when
        MvcResult result = mockMvc.perform(get("/v1/article/" + articleId)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        // then
        String content = result.getResponse().getContentAsString();
        ArticleResponseDto dto = objectMapper.readValue(content, ArticleResponseDto.class);
        assertEquals(dto.getArticleId(), articleId);
    }

    @Test
    @DisplayName("[Update] 게시물 업데이트 - 성공")
    void updateArticleSuccess() throws Exception {
        // given
        String articleId = UUID.randomUUID().toString();
        ArticleUpdateRequestDto requestDto = createDummyArticleUpdateRequestDto();

        // when
        MvcResult result = mockMvc.perform(put("/v1/article/" + articleId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        // then
        String content = result.getResponse().getContentAsString();
        ArticleResponseDto dto = objectMapper.readValue(content, ArticleResponseDto.class);
        assertEquals(dto.getArticleId(), articleId);
        assertEquals(dto.getTitle(), requestDto.getTitle());
        assertEquals(dto.getContent(), requestDto.getContent());
    }

    @Test
    @DisplayName("[Delete] 게시물 삭제 - 성공")
    void deleteArticleSuccess() throws Exception {
        // given
        String articleId = UUID.randomUUID().toString();

        // when
        MvcResult result = mockMvc.perform(delete("/v1/article/" + articleId)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        // then
        assertEquals(result.getResponse().getStatus(), HttpServletResponse.SC_OK);
    }

    @Test
    @DisplayName("[Create] 좋아요 추가 - 성공")
    void createLikeSuccess() throws Exception {
        // given
        String articleId = UUID.randomUUID().toString();

        // when
        MvcResult result = mockMvc.perform(post("/v1/article/like/" + articleId)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        // then
        assertEquals(result.getResponse().getStatus(), HttpServletResponse.SC_OK);
    }
    @Test
    @DisplayName("[Delete] 좋아요 삭제 - 성공")
    void deleteLikeSuccess() throws Exception {
        // given
        String articleId = UUID.randomUUID().toString();

        // when
        MvcResult result = mockMvc.perform(delete("/v1/article/like/" + articleId)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        // then
        assertEquals(result.getResponse().getStatus(), HttpServletResponse.SC_OK);
    }

    public static ArticleCreateRequestDto createDummyArticleCreateQuestDto() {
        return ArticleCreateRequestDto.builder()
                .title("test-title")
                .content("test-content").build();
    }

    public static ArticleUpdateRequestDto createDummyArticleUpdateRequestDto() {
        return ArticleUpdateRequestDto.builder()
                .title("test-title")
                .content("test-content").build();
    }
}
