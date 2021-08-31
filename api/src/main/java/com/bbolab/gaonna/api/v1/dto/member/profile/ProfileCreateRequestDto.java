package com.bbolab.gaonna.api.v1.dto.member.profile;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileCreateRequestDto {
    private String profileName;
    private String description;
    private String profileImageId;
}
