package com.bbolab.gaonna.api.v1.dto.quest;

import com.bbolab.gaonna.api.v1.dto.category.CategoryDto;
import com.bbolab.gaonna.api.v1.dto.member.MemberDto;
import com.bbolab.gaonna.api.v1.dto.region.RegionDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestListResponseItemDto {
    private MemberDto requester;

    private String articleId;

    private String questId;

    private String title;

    private Integer price;

    private LocalDateTime lastModifiedDate;

    private LocalDateTime deadline;

    private List<Double> location = new ArrayList<>();

    private List<String> tags;

    private List<CategoryDto> categories;

    private List<RegionDto> regions;

    public void coordinateToList(double longitude, double latitude) {
        location.clear();
        location.add(longitude);
        location.add(latitude);
    }
}
