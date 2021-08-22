package com.bbolab.gaonna.api.v1.controller;

import com.bbolab.gaonna.api.v1.dto.article.ArticleResponseDto;
import com.bbolab.gaonna.api.v1.dto.category.CategoryDto;
import com.bbolab.gaonna.api.v1.dto.comment.CommentResponseDto;
import com.bbolab.gaonna.api.v1.dto.quest.QuestDetailResponseDto;

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
                .commentCount(1)
                .comments(Collections.singletonList(CommentResponseDto.builder()
                        .memberId(UUID.randomUUID().toString())
                        .memberName("test-member-name")
                        .content("test-comment-content")
                        .updatedTime(LocalDateTime.now()).build())).build();
    }

    public static CommentResponseDto createDummyComment() {
        return CommentResponseDto.builder()
                .memberId(UUID.randomUUID().toString())
                .memberName("test-name")
                .content("test-comment-content")
                .updatedTime(LocalDateTime.now()).build();
    }

    public static CommentResponseDto createDummyCommentResponseDto() {
        return CommentResponseDto.builder()
                .commentId(UUID.randomUUID().toString())
                .memberId(UUID.randomUUID().toString())
                .memberName("test-member-name")
                .content("test-comment-content")
                .updatedTime(LocalDateTime.now()).build();
    }

    public static QuestDetailResponseDto createDummyQuestResponseDto() {
        return QuestDetailResponseDto.builder()
                .articleId(UUID.randomUUID().toString())
                .title("test-title")
                .content("test-content-with-html-format")
                .updatedTime(LocalDateTime.now())
                .likeCount(3)
                .comments(Collections.singletonList(CommentResponseDto.builder()
                        .commentId(UUID.randomUUID().toString())
                        .memberId(UUID.randomUUID().toString())
                        .memberName("test-member-name")
                        .content("test-comment-content")
                        .updatedTime(LocalDateTime.now()).build()))
                .questId(UUID.randomUUID().toString())
                .longitude(33.232323)
                .latitude(121.121213)
                .price(10000)
                .deadline(LocalDateTime.now())
                .tags(Arrays.asList("tag1", "tag2"))
                .categories(Collections.singletonList(CategoryDto.builder().key("key").value("value").build()))
                .build();
    }
}
