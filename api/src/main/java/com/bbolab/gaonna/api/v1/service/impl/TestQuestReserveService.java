package com.bbolab.gaonna.api.v1.service.impl;

import com.bbolab.gaonna.api.v1.dto.reserve.ReserveQuestRequestDto;
import com.bbolab.gaonna.api.v1.dto.reserve.ReserveQuestResponseDto;
import com.bbolab.gaonna.api.v1.service.QuestReserveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.bbolab.gaonna.api.v1.controller.MockFactoryUtil.createDummyReserveQuestResponseDto;


@Service
@RequiredArgsConstructor
public class TestQuestReserveService implements QuestReserveService {
    @Override
    public ReserveQuestResponseDto getReservationStatusById(UUID questId, UUID reserveId) {
        ReserveQuestResponseDto dto = createDummyReserveQuestResponseDto();
        dto.setQuestId(questId.toString());
        dto.setReserveId(reserveId.toString());
        return dto;
    }

    @Override
    public ReserveQuestResponseDto addReserveRequestToQuest(UUID memberId, UUID questId, ReserveQuestRequestDto requestDto) {
        ReserveQuestResponseDto dto = createDummyReserveQuestResponseDto();
        dto.setQuestId(questId.toString());
        dto.setProfileId(requestDto.getProfileId());
        return dto;
    }

    @Override
    public boolean cancelReservation(UUID memberId, UUID questId, UUID reserveId) {
        return true;
    }

    @Override
    public boolean acceptReservation(UUID memberId, UUID reserveId) {
        return true;
    }
}
