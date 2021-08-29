package com.bbolab.gaonna.api.v1.dto.quest;

import com.bbolab.gaonna.api.v1.dto.member.profile.ProfileDto;

public class QuestPerformerDto {
    private String memberId;

    private String nickname;

    private ProfileDto profile;

    private Boolean isAccepted; // 해당 퀘스트 수행 요청이 퀘스트 요청자에 의해 수락되었는지 여부
}
