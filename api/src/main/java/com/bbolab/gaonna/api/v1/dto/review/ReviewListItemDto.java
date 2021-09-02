package com.bbolab.gaonna.api.v1.dto.review;

import com.bbolab.gaonna.api.v1.dto.member.MemberDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewListItemDto {
    private String reviewId;
    private MemberDto reviewer;
    private Double score;
    private String content;
}
