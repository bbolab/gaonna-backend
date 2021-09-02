package com.bbolab.gaonna.api.v1.dto.member.profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class ProfileCreateRequestDto {
    private String profileName;
    private String description;
    private String profileImageId;
}
