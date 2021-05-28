package com.bbolab.gaonna.api.v1.dto.article;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentDto {
    String memberId;

    String memberName;

    String content;

    LocalDateTime updatedTime;
}
