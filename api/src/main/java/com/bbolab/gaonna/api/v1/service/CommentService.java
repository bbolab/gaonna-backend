package com.bbolab.gaonna.api.v1.service;

import com.bbolab.gaonna.api.v1.dto.comment.CommentListResponseDto;
import com.bbolab.gaonna.api.v1.dto.comment.CommentResponseDto;

import java.util.UUID;

public interface CommentService {
    public CommentListResponseDto getAllArticleComments(UUID userId, UUID articleId);

    public CommentResponseDto updateComment(UUID userId, UUID commentId, String content);

    // parentId is null if comment has no parent
    public CommentResponseDto createComment(UUID userId, UUID articleId, UUID parentId, String content);

    public boolean deleteComment(UUID userId, UUID commentId);
}
