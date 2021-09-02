package com.bbolab.gaonna.api.v1.dto.member.profile;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileDto {
    private String profileId;

    private String profileName;

    private String description;

    private Double score; // 해당 프로필에 대한 평점

    private String profileImageId;
}

//[nickname, profile image, profile id, profile name, profile description, profile score(평점),  ]

