package com.bbolab.gaonna.api.v1.dto.quest;

import com.bbolab.gaonna.api.v1.dto.category.CategoryDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class QuestCreateRequestDto {
    // Article information
    String title;

    String content;

    // Quest information
    double latitude;

    double longitude;

    int price;

    LocalDateTime deadline;

    List<String> tags;

    List<CategoryDto> categories; // Category 필드는 key-value 형태여야 함(e.g. 노동타입 : 운전)

    // TODO : 지역 정보를 입력으로 받을지(e.g. 하동, 광교동 등), 아니면 전달받은 좌표값 기준으로 서버에서 알아서 생성할지 고민 필요
}
