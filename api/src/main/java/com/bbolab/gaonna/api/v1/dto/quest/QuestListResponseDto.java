package com.bbolab.gaonna.api.v1.dto.quest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestListResponseDto {
    List<QuestListResponseItemDto> quests;
}
