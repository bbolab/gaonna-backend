package com.bbolab.gaonna.api.v1.dto.quest;

import com.bbolab.gaonna.api.v1.dto.category.CategoryDto;
import com.bbolab.gaonna.api.v1.dto.region.RegionDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestListResponseItemDto {
    // Article information
    private String articleId;

    private String title;

    // Quest infromation
    private String questId;

    private double longitude;

    private double latitude;

    private int price;

    private LocalDateTime deadline;

    private List<String> tags;

    private List<CategoryDto> categories;

    private List<RegionDto> regions;
}
