package com.bbolab.gaonna.api.v1.dto.quest;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class QuestListItemDto {
    private String questId;
    private String title;
    private LocalDateTime createdTime;
    private LocalDateTime deadline;
}
