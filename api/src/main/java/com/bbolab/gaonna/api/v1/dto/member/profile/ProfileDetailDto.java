package com.bbolab.gaonna.api.v1.dto.member.profile;

import com.bbolab.gaonna.api.v1.dto.member.MemberDto;
import com.bbolab.gaonna.api.v1.dto.quest.QuestListItemDto;
import com.bbolab.gaonna.api.v1.dto.review.ReviewListItemDto;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ProfileDetailDto {
    private String profileId;

    private String profileName;

    private String description;

    private MemberDto member;

    private Integer profileQuestCount;
    private List<QuestListItemDto> profileQuests = new ArrayList<>();

    private Double score; // 해당 프로필에 대한 평점

    private String profileImageId;

    private Integer reviewCount;
    private List<ReviewListItemDto> reviews = new ArrayList<>();
}
