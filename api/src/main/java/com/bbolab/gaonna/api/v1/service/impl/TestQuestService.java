package com.bbolab.gaonna.api.v1.service.impl;

import com.bbolab.gaonna.api.v1.controller.validator.BboxConstraintValidator;
import com.bbolab.gaonna.api.v1.dto.quest.QuestDetailResponseDto;
import com.bbolab.gaonna.api.v1.dto.quest.QuestListResponseItemDto;
import com.bbolab.gaonna.api.v1.dto.quest.QuestRequestDto;
import com.bbolab.gaonna.api.v1.service.QuestService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static com.bbolab.gaonna.api.v1.controller.MockFactoryUtil.createDummyQuestListItemDto;
import static com.bbolab.gaonna.api.v1.controller.MockFactoryUtil.createDummyQuestResponseDto;

@Service
@RequiredArgsConstructor
public class TestQuestService implements QuestService {

    private final ModelMapper modelMapper;

    @Override
    public QuestDetailResponseDto getQuestDetail(UUID memberId, UUID questId) {
        QuestDetailResponseDto dto = createDummyQuestResponseDto();
        dto.setQuestId(questId.toString());
        return dto;
    }

    @Override
    public List<QuestListResponseItemDto> getQuestsInBboxString(UUID memberId, String bbox) {
        Double[][] bboxArr = BboxConstraintValidator.parseBboxStringToDoubleArray(bbox);
        Double minX = bboxArr[0][0];
        Double minY = bboxArr[0][1];
        Double maxX = bboxArr[1][0];
        Double maxY = bboxArr[1][1];

        QuestListResponseItemDto info1 = modelMapper.map(createDummyQuestListItemDto(), QuestListResponseItemDto.class);
        QuestListResponseItemDto info2 = modelMapper.map(createDummyQuestListItemDto(), QuestListResponseItemDto.class);

        List<QuestListResponseItemDto> items = new LinkedList<>();
        items.add(info1);
        items.add(info2);

        return items;
    }

    @Override
    public QuestDetailResponseDto createQuest(UUID memberId, QuestRequestDto questInfo) {
        QuestDetailResponseDto dto = createDummyQuestResponseDto();
        modelMapper.map(questInfo, dto);
        return dto;
    }

    @Override
    public QuestDetailResponseDto updateQuest(UUID memberId, UUID questId, QuestRequestDto questInfo) {
        QuestDetailResponseDto questDetailResponseDto = createDummyQuestResponseDto();
        modelMapper.map(questInfo, questDetailResponseDto);
        questDetailResponseDto.setQuestId(questId.toString());
        return null;
    }

    @Override
    public boolean deleteQuest(UUID memberId, UUID questId) {
        return true;
    }
}
