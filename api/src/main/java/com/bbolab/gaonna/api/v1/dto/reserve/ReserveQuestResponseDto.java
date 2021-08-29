package com.bbolab.gaonna.api.v1.dto.reserve;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReserveQuestResponseDto {
    private String memberId;
    private String nickname;
    private String reserveId; // MemberQuestPerformer Id
    private String questId;
    private String profileId;
    private Boolean isAccepted;

}
