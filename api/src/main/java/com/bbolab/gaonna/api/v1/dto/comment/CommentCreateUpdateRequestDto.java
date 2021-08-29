package com.bbolab.gaonna.api.v1.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateUpdateRequestDto {
    private Boolean isSubComment;

    private String parentId; // if "isSubComment", parentId should exist

    private String content;
}