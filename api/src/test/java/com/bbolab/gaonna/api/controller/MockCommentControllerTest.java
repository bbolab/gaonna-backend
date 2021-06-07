package com.bbolab.gaonna.api.controller;

import com.bbolab.gaonna.api.AbstractContainerBaseTest;
import com.bbolab.gaonna.api.MockMvcTest;
import com.bbolab.gaonna.api.v1.dto.comment.CommentCreateUpdateRequestDto;
import com.bbolab.gaonna.api.v1.dto.comment.CommentListResponseDto;
import com.bbolab.gaonna.api.v1.dto.comment.CommentResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
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
public class MockCommentControllerTest extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("[Get] 게시글 댓글 조회 - 성공")
    void getAllCommentSuccess() throws Exception {
        // given
        String articleId = UUID.randomUUID().toString();
        String url = String.format("/v1/article/%s/comment", articleId);

        // when
        MvcResult result = mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        // then
        String content = result.getResponse().getContentAsString();
        CommentListResponseDto dto = objectMapper.readValue(content, CommentListResponseDto.class);

        assertEquals(dto.getArticleId(), articleId);
        assertNotNull(dto.getCommentLists());
        assertNotNull(dto.getCommentLists().get(0).getContent());
    }

    @Test
    @DisplayName("[Create] 댓글 추가 - 성공")
    void createCommentSuccess() throws Exception {
        // given
        String articleId = UUID.randomUUID().toString();
        CommentCreateUpdateRequestDto requestDto = createCommentCreateRequestDto();
        String url = String.format("/v1/article/%s/comment", articleId);

        // when
        MvcResult result = mockMvc.perform(post(url)
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
        String articleId = UUID.randomUUID().toString();
        String commentId = UUID.randomUUID().toString();
        CommentCreateUpdateRequestDto requestDto = createCommentCreateRequestDto();
        String url = String.format("/v1/article/%s/comment/%s", articleId, commentId);

        // when
        MvcResult result = mockMvc.perform(put(url)
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
        String articleId = UUID.randomUUID().toString();
        String commentId = UUID.randomUUID().toString();
        String url = String.format("/v1/article/%s/comment/%s", articleId, commentId);

        // when & then
        MvcResult result = mockMvc.perform(delete(url)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        // then
        assertEquals(result.getResponse().getStatus(), HttpServletResponse.SC_OK);
    }

    public static CommentCreateUpdateRequestDto createCommentCreateRequestDto() {
        return CommentCreateUpdateRequestDto.builder()
                .content("test-comment-content").build();
    }

}
