package com.bbolab.gaonna.api.v1.service.impl;

import com.bbolab.gaonna.api.v1.dto.article.ArticleCreateRequestDto;
import com.bbolab.gaonna.api.v1.dto.article.ArticleResponseDto;
import com.bbolab.gaonna.api.v1.dto.article.ArticleUpdateRequestDto;
import com.bbolab.gaonna.api.v1.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.bbolab.gaonna.api.v1.controller.MockFactoryUtil.createDummyArticleResponseDto;

@Service
@RequiredArgsConstructor
public class TestArticleService implements ArticleService {

    private final ModelMapper modelMapper;

    @Override
    public ArticleResponseDto searchArticleById(UUID articleId) {
        ArticleResponseDto dto = createDummyArticleResponseDto();
        dto.setArticleId(articleId.toString());
        return dto;
    }

    @Override
    public ArticleResponseDto createNewArticle(UUID userId, ArticleCreateRequestDto articleDto) {
        ArticleResponseDto dto = createDummyArticleResponseDto();
        modelMapper.map(articleDto, dto);
        dto.setArticleId(userId.toString());
        return dto;
    }

    @Override
    public ArticleResponseDto updateArticleById(UUID userId, UUID articleId, ArticleUpdateRequestDto articleDto) {
        ArticleResponseDto dto = createDummyArticleResponseDto();
        modelMapper.map(articleDto, dto);
        dto.setArticleId(articleId.toString());
        return dto;
    }

    @Override
    public boolean deleteArticleById(UUID userId, UUID articleId) {
        return true;
    }

    @Override
    public boolean addLikeToArticle(UUID userId, UUID articleId) {
        return true;
    }

    @Override
    public boolean cancelLikeToArticle(UUID userId, UUID articleId) {
        return true;
    }
}
