package com.bbolab.gaonna.api.v1.dto.member;

import com.bbolab.gaonna.api.v1.dto.member.profile.ProfileDto;
import com.bbolab.gaonna.api.v1.dto.quest.QuestListItemDto;
import com.bbolab.gaonna.api.v1.dto.review.ReviewListItemDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class MemberInfoDto {
    private String memberId;
    private String nickname;
    private LocalDateTime joinDate;

    private Integer requestedQuestCount;
    private List<QuestListItemDto> requestedQuest;

    private Integer performedQuestCount;
    private List<QuestListItemDto> performedQuest;

    private Integer profilesCount;
    private List<ProfileDto> profiles;

    private Integer reviewCount;
    private List<ReviewListItemDto> reviews;
}
