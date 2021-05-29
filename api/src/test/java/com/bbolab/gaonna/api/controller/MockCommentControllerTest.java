package com.bbolab.gaonna.api.controller;

import com.bbolab.gaonna.api.MockMvcTest;
import com.bbolab.gaonna.api.v1.dto.comment.CommentCreateUpdateRequestDto;
import com.bbolab.gaonna.api.v1.dto.comment.CommentResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@MockMvcTest
public class MockCommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("[Create] 댓글 추가 - 성공")
    void createCommentSuccess() throws Exception {
        // given
        String articleId = UUID.randomUUID().toString();
        CommentCreateUpdateRequestDto requestDto = createCommentCreateRequestDto();

        // when
        MvcResult result = mockMvc.perform(post("/v1/comment/" + articleId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        // then
        String content = result.getResponse().getContentAsString();
        CommentResponseDto dto = objectMapper.readValue(content, CommentResponseDto.class);
        assertEquals(dto.getContent(), requestDto.getContent());
    }

    @Test
    @DisplayName("[Update] 댓글 수정 - 성공")
    void updateCommentSuccess() throws Exception {
        // given
        String commentId = UUID.randomUUID().toString();
        CommentCreateUpdateRequestDto requestDto = createCommentCreateRequestDto();

        // when
        MvcResult result = mockMvc.perform(put("/v1/comment/" + commentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        // then
        String content = result.getResponse().getContentAsString();
        CommentResponseDto dto = objectMapper.readValue(content, CommentResponseDto.class);
        assertEquals(dto.getContent(), requestDto.getContent());
    }

    @Test
    @DisplayName("[Delete] 댓글 삭제 - 성공")
    void deleteCommentSuccess() throws Exception {
        // given
        String commentId = UUID.randomUUID().toString();

        // when & then
        mockMvc.perform(delete("/v1/comment/" + commentId)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();
    }

    public static CommentCreateUpdateRequestDto createCommentCreateRequestDto() {
        return CommentCreateUpdateRequestDto.builder()
                .content("test-comment-content").build();
    }

}
