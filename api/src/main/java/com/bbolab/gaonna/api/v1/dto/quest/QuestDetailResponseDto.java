package com.bbolab.gaonna.api.v1.dto.quest;

import com.bbolab.gaonna.api.v1.dto.comment.CommentResponseDto;
import com.bbolab.gaonna.api.v1.dto.category.CategoryDto;
import com.bbolab.gaonna.api.v1.dto.member.MemberDto;
import com.bbolab.gaonna.api.v1.dto.region.RegionDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestDetailResponseDto {
    private MemberDto requester;

    private String questId;

    private String title;

    private String content;

    private LocalDateTime updatedTime;

    private LocalDateTime deadline;

    private Integer price;

    private List<Double> location = new ArrayList<>();

    private Integer likeCount;

    private Boolean isLiked;  // 본인이 해당 퀘스트에 좋아요를 눌렀는지

    private Integer commentCount;

    private List<CommentResponseDto> comments;

    private List<String> tags;

    private List<CategoryDto> categories;

    private List<String> imageIds;

    private List<RegionDto> regions;

    private Boolean isReported; // 본인이 해당 퀘스트를 신고했는지

    private Boolean isReserved;

    private Boolean isMyQuest; // 해당 퀘스트의 요청자가 본인인지

    private List<QuestPerformerDto> performRequests = new ArrayList<>(); // only has values if "isMyQuest" is true
}
