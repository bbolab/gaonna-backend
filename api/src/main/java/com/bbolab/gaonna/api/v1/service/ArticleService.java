package com.bbolab.gaonna.api.v1.service;

import com.bbolab.gaonna.api.v1.dto.article.ArticleCreateRequestDto;
import com.bbolab.gaonna.api.v1.dto.article.ArticleResponseDto;
import com.bbolab.gaonna.api.v1.dto.article.ArticleUpdateRequestDto;

import java.util.UUID;

public interface ArticleService {

    // return null if there is no article with articleId
    ArticleResponseDto searchArticleById(UUID articleId);

    ArticleResponseDto createNewArticle(UUID userId, ArticleCreateRequestDto articleDto);

    // return null if user has no permission to update the article or if there is no article with articleId
    ArticleResponseDto updateArticleById(UUID userId, UUID articleId, ArticleUpdateRequestDto articleDto);

    boolean deleteArticleById(UUID userId, UUID articleId);

    // TODO : 해당 요청을 한 유저가 Article에 좋아요를 이미 눌렀을 경우는 예외
    boolean addLikeToArticle(UUID userId, UUID articleId);

    // TODO : 해당 요청을 한 유저가 Article에 좋아요를 누르지 않았을 경우는 예외
    boolean cancelLikeToArticle(UUID userId, UUID articleId);
}
