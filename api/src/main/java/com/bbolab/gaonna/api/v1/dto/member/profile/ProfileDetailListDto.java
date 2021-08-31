package com.bbolab.gaonna.api.v1.dto.member.profile;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ProfileDetailListDto {
    private Integer profileCount;
    private List<ProfileDetailDto> profiles = new ArrayList<>();
}
