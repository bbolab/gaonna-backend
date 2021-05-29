package com.bbolab.gaonna.api.v1.dto.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    String memberId;

    String memberName;

    String content;

    LocalDateTime updatedTime;
}
