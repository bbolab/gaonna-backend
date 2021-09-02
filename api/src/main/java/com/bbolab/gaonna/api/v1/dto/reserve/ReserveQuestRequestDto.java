package com.bbolab.gaonna.api.v1.dto.reserve;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class ReserveQuestRequestDto {
    private String profileId;
}
