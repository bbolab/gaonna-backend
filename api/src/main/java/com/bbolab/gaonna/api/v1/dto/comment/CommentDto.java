package com.bbolab.gaonna.api.v1.dto.comment;

import java.time.LocalDateTime;

public class CommentDto {
    private String commentId;

    private String memberId;

    private String memberName;

    private String content;

    private LocalDateTime updatedTime;

    private Boolean isMine;
}
