package com.bbolab.gaonna.api.v1.service;

import com.bbolab.gaonna.api.v1.dto.quest.QuestDetailResponseDto;
import com.bbolab.gaonna.api.v1.dto.quest.QuestListResponseItemDto;
import com.bbolab.gaonna.api.v1.dto.quest.QuestRequestDto;

import java.util.List;
import java.util.UUID;

public interface QuestService {

    public QuestDetailResponseDto getQuestDetail(UUID memberId, UUID questId);

    public List<QuestListResponseItemDto> getQuestsInBboxString(UUID memberId, String bbox);

    public QuestDetailResponseDto createQuest(UUID memberId, QuestRequestDto questInfo);

    public QuestDetailResponseDto updateQuest(UUID memberId, UUID questId, QuestRequestDto questInfo);

    public boolean deleteQuest(UUID memberId, UUID questId);
}
