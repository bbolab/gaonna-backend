package com.bbolab.gaonna.api.v1.dto.quest;

import com.bbolab.gaonna.api.v1.dto.article.CommentDto;
import com.bbolab.gaonna.api.v1.dto.category.CategoryDto;
import com.bbolab.gaonna.api.v1.dto.region.RegionDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class QuestResponseDto {

    // Article information
    String articleId;

    String title;

    String content;

    LocalDateTime updatedTime;

    long likeCount;

    List<CommentDto> comments;

    // Quest information
    String questId;

    double longitude;

    double latitude;

    int price;

    LocalDateTime deadline;

    List<String> tags;

    List<CategoryDto> categories;

    List<RegionDto> regions;
}
