package com.bbolab.gaonna.api.v1.service;

import com.bbolab.gaonna.api.v1.dto.reserve.ReserveQuestRequestDto;
import com.bbolab.gaonna.api.v1.dto.reserve.ReserveQuestResponseDto;

import java.util.UUID;

public interface QuestReserveService {

    public ReserveQuestResponseDto getReservationStatusById(UUID questId, UUID reserveId);


    // TODO : 1. check if profile Id belongs to requested member
    // TODO : 2. check if member already requested reservation as performer to the {quest Id}
    // TODO :   2.1 drop request if member already requested reservation to the {quest Id}
    // TODO :   2.2 add MemberQuestPerformer if member never requested the reservation to the {quest Id}
    public ReserveQuestResponseDto addReserveRequestToQuest(UUID memberId, UUID questId, ReserveQuestRequestDto requestDto);


    public boolean cancelReservation(UUID memberId, UUID questId, UUID reserveId);

    public boolean acceptReservation(UUID memberId, UUID reserveId);
}
