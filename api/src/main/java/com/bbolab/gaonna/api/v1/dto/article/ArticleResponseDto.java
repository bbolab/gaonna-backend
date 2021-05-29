package com.bbolab.gaonna.api.v1.dto.article;

import com.bbolab.gaonna.api.v1.dto.comment.CommentResponseDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ArticleResponseDto {
    // Article information
    private String articleId;

    private String title;

    private String content;

    private LocalDateTime updatedTime;

    private long likeCount;

    private long commentCount;

    private List<CommentResponseDto> comments;
}
