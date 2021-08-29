package com.bbolab.gaonna.api.v1.dto.comment;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentDto {
    private String commentId;

    private String memberId;

    private String memberName;

    private String content;

    private LocalDateTime updatedTime;

    private Boolean isMine;
}
