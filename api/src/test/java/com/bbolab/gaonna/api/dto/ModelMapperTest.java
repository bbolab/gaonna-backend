package com.bbolab.gaonna.api.dto;

import com.bbolab.gaonna.api.controller.MockMvcTest;
import com.bbolab.gaonna.api.dto.article.ArticleDto;
import com.bbolab.gaonna.core.domain.article.Article;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MockMvcTest
public class ModelMapperTest {

    @Autowired
    private ModelMapper modelMapper;

    @Test
    @DisplayName("ModelMapper Bean 테스트")
    public void modelMapperBean() {
        ArticleDto dto = ArticleDto.builder()
                .title("test-title")
                .content("test-content")
                .updatedTime(LocalDateTime.now())
                .createdTime(LocalDateTime.now()).build();

        Article article = modelMapper.map(dto, Article.class);

        assertEquals(article.getTitle(), dto.getTitle());
        assertEquals(article.getContent(), dto.getContent());
        assertEquals(article.getCreatedTime(), dto.getCreatedTime());
        assertEquals(article.getUpdatedTime(), dto.getUpdatedTime());
    }

}
