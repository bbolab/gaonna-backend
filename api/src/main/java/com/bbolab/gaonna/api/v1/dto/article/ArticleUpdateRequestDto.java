package com.bbolab.gaonna.api.v1.dto.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleUpdateRequestDto {
    String title;
    String content;
}
