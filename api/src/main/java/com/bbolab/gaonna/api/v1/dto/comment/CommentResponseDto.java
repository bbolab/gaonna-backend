package com.bbolab.gaonna.api.v1.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {
    private String commentId;

    private String memberId;

    private String memberName;

    private String content;

    private LocalDateTime updatedTime;
}