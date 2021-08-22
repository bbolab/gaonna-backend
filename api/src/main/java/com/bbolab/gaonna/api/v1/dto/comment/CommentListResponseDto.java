package com.bbolab.gaonna.api.v1.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentListResponseDto {
    private String articleId;
    private List<CommentResponseDto> commentLists;
}
