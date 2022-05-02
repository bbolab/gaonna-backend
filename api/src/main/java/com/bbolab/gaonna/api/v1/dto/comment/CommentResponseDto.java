package com.bbolab.gaonna.api.v1.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {
    private String articleId;

    private CommentDto comment;

    private Integer nSubComment;

    private List<CommentDto> subComments = new ArrayList<>();

    private boolean isMine;
}
