package com.bbolab.gaonna.api.v1.controller;

import com.bbolab.gaonna.api.v1.dto.article.ArticleResponseDto;
import com.bbolab.gaonna.api.v1.dto.category.CategoryDto;
import com.bbolab.gaonna.api.v1.dto.comment.CommentDto;
import com.bbolab.gaonna.api.v1.dto.comment.CommentResponseDto;
import com.bbolab.gaonna.api.v1.dto.member.MemberDto;
import com.bbolab.gaonna.api.v1.dto.member.MemberInfoDto;
import com.bbolab.gaonna.api.v1.dto.member.profile.ProfileDto;
import com.bbolab.gaonna.api.v1.dto.quest.QuestDetailResponseDto;
import com.bbolab.gaonna.api.v1.dto.quest.QuestListItemDto;
import com.bbolab.gaonna.api.v1.dto.quest.QuestListResponseItemDto;
import com.bbolab.gaonna.api.v1.dto.quest.QuestPerformerDto;
import com.bbolab.gaonna.api.v1.dto.region.RegionDto;
import com.bbolab.gaonna.api.v1.dto.reserve.ReserveQuestResponseDto;
import com.bbolab.gaonna.api.v1.dto.review.ReviewListItemDto;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

public class MockFactoryUtil {

    public static ArticleResponseDto createDummyArticleResponseDto() {
        return ArticleResponseDto.builder()
                .articleId(UUID.randomUUID().toString())
                .title("test-title")
                .content("test-content-with-html-format")
                .updatedTime(LocalDateTime.now())
                .likeCount(3)
                .commentCount(2)
                .comments(Collections.singletonList(createDummyCommentResponseDto()))
                .build();
    }

    public static QuestDetailResponseDto createDummyQuestResponseDto() {
        return QuestDetailResponseDto.builder()
                .requester(createDummyMemberDto())
                .articleId(UUID.randomUUID().toString())
                .questId(UUID.randomUUID().toString())
                .title("test-title")
                .content("test-content-with-html-format")
                .updatedTime(LocalDateTime.now())
                .deadline(LocalDateTime.now())
                .price(10000)
                .location(Arrays.asList(127.0403165, 37.2746168))
                .likeCount(5)
                .isLiked(true)
                .commentCount(2)
                .comments(Collections.singletonList(createDummyCommentResponseDto()))
                .tags(Arrays.asList("tag1", "tag2"))
                .categories(Collections.singletonList(CategoryDto.builder().key("key").value("value").build()))
                .imageIds(Arrays.asList(UUID.randomUUID().toString(), UUID.randomUUID().toString()))
                .isReported(true)
                .isReserved(true)
                .isMyQuest(true)
                .nPerformRequest(2)
                .performRequests(Arrays.asList(
                        createDummyPerformRequests("memberA", false),
                        createDummyPerformRequests("memberB", true)))
                .build();
    }

    public static QuestListResponseItemDto createDummyQuestListItemDto() {
        return QuestListResponseItemDto.builder()
                .requester(createDummyMemberDto())
                .articleId(UUID.randomUUID().toString())
                .questId(UUID.randomUUID().toString())
                .title("test-title")
                .price(10000)
                .lastModifiedDate(LocalDateTime.now())
                .deadline(LocalDateTime.now())
                .location(Arrays.asList(127.0403165, 37.2746168))
                .tags(Arrays.asList("tag1", "tag2"))
                .categories(Collections.singletonList(CategoryDto.builder().key("key").value("value").build()))
                .build();
    }

    public static MemberDto createDummyMemberDto() {
        return MemberDto.builder()
                .memberId(UUID.randomUUID().toString())
                .nickname("nickname")
                .imgUri("user's profile uri")
                .build();
    }

    public static CommentResponseDto createDummyCommentResponseDto() {
        CommentDto parent = CommentDto.builder()
                .commentId(UUID.randomUUID().toString())
                .memberId(UUID.randomUUID().toString())
                .memberName("memberA")
                .content("comment is comment")
                .updatedTime(LocalDateTime.now())
                .isMine(false)
                .build();

        CommentDto child1 = CommentDto.builder()
                .commentId(UUID.randomUUID().toString())
                .memberId(UUID.randomUUID().toString())
                .memberName("memberB")
                .content("comment to comment, this is sub comment")
                .updatedTime(LocalDateTime.now())
                .isMine(true)
                .build();

        return CommentResponseDto.builder()
                .comment(parent)
                .nSubComment(1)
                .subComments(Collections.singletonList(child1)).build();
    }
    public static QuestPerformerDto createDummyPerformRequests(String nickname, Boolean accepted) {
        return QuestPerformerDto.builder()
                .memberId(UUID.randomUUID().toString())
                .nickname(nickname)
                .profile(createDummyProfileDto())
                .isAccepted(accepted)
                .build();
    }

    private static ProfileDto createDummyProfileDto() {
        return ProfileDto.builder()
                .profileId(UUID.randomUUID().toString())
                .profileImageId(UUID.randomUUID().toString())
                .profileName("baemin rider")
                .description("i can do everything with my bike.")
                .score(3.5)
                .build();
    }

    public static ReserveQuestResponseDto createDummyReserveQuestResponseDto() {
        return ReserveQuestResponseDto.builder()
                .memberId(UUID.randomUUID().toString())
                .nickname("memberA")
                .reserveId(UUID.randomUUID().toString())
                .questId(UUID.randomUUID().toString())
                .profileId(UUID.randomUUID().toString())
                .isAccepted(false)
                .build();
    }

    public static MemberInfoDto createDummyMemberInfoDto(String memberId) {
        return MemberInfoDto.builder()
                .memberId(memberId)
                .nickname("nickname test")
                .joinDate(LocalDateTime.now())
                .requestedQuestCount(1)
                .requestedQuest(Collections.singletonList(createDummyQuestListItem()))
                .performedQuestCount(1)
                .performedQuest(Collections.singletonList(createDummyQuestListItem()))
                .profilesCount(1)
                .profiles(Collections.singletonList(createDummyProfileDto()))
                .reviewCount(1)
                .reviews(Collections.singletonList(createDummyReview()))
                .build();
    }

    private static QuestListItemDto createDummyQuestListItem() {
        return QuestListItemDto.builder()
                .questId(UUID.randomUUID().toString())
                .title("test quest title")
                .createdTime(LocalDateTime.now())
                .deadline(LocalDateTime.now())
                .build();
    }

    private static ReviewListItemDto createDummyReview() {
        return ReviewListItemDto.builder()
                .reviewId(UUID.randomUUID().toString())
                .reviewer(createDummyMemberDto())
                .score(3.5)
                .content("performance was good")
                .build();
    }
}
