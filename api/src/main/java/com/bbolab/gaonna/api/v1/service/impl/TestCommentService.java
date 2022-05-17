package com.bbolab.gaonna.api.v1.service.impl;

import com.bbolab.gaonna.api.v1.dto.comment.CommentListResponseDto;
import com.bbolab.gaonna.api.v1.dto.comment.CommentResponseDto;
import com.bbolab.gaonna.api.v1.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;

import static com.bbolab.gaonna.api.v1.controller.MockFactoryUtil.createDummyCommentResponseDto;

@Service
public class TestCommentService implements CommentService {
    @Override
    public CommentListResponseDto getAllArticleComments(UUID userId, UUID articleId) {
        CommentResponseDto comment1 = createDummyCommentResponseDto();
        CommentResponseDto comment2 = createDummyCommentResponseDto();
        CommentListResponseDto dto = CommentListResponseDto.builder()
                .articleId(articleId.toString())
                .nComments(comment1.getNSubComment() + comment2.getNSubComment())
                .commentLists(Arrays.asList(comment1, comment2))
                .build();
        return dto;
    }

    @Override
    public CommentResponseDto updateComment(UUID userId, UUID commentId, String content) {
        CommentResponseDto dto = createDummyCommentResponseDto();
        dto.getComment().setContent(content);
        return dto;
    }

    @Override
    public CommentResponseDto createComment(UUID userId, UUID articleId, UUID parentId, String content) {
        CommentResponseDto dto = createDummyCommentResponseDto();
        dto.setArticleId(articleId.toString());
        if(parentId != null) {
            dto.setNSubComment(0);
            return dto;
        }
        return null;
    }

    @Override
    public boolean deleteComment(UUID userId, UUID commentId) {
        return true;
    }
}
