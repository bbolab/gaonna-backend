package com.bbolab.gaonna.api.dto.article;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ArticleDto {
    public String title;
    public String content;
    public LocalDateTime createdTime;
    public LocalDateTime updatedTime;
}
