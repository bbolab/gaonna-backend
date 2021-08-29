package com.bbolab.gaonna.api.v1.dto.reserve;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReserveQuestRequestDto {
    private String profileId;
}
