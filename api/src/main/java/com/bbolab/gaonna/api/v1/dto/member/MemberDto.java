package com.bbolab.gaonna.api.v1.dto.member;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberDto {
    private String memberId;
    private String nickname;
    private String imgUri;
    private Double score;
}
