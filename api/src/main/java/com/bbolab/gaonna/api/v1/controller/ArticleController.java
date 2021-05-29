package com.bbolab.gaonna.api.v1.controller;

import com.bbolab.gaonna.api.v1.dto.article.ArticleCreateRequestDto;
import com.bbolab.gaonna.api.v1.dto.article.ArticleResponseDto;
import com.bbolab.gaonna.api.v1.dto.article.ArticleUpdateRequestDto;
import com.bbolab.gaonna.api.v1.dto.comment.CommentResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

@RestController
@RequestMapping("/v1/article")
@RequiredArgsConstructor
public class ArticleController {
    // Quest가 없는 일반 게시물을 조회하는 경우
    // Quest가 있는 경우 Quest Controller 통해서 조회해야 함
    private final ModelMapper modelMapper;

    @GetMapping("{articleId}")
    public ResponseEntity<?> get(@PathVariable String articleId) {
        // TODO : Quest가 있는 Article에 대해서 articleId로 조회를 할 경우 어떻게 할 것인지?
        ArticleResponseDto dto = createDummyArticleResponseDto();
        dto.setArticleId(articleId);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ArticleCreateRequestDto requestDto) {
        ArticleResponseDto dto = createDummyArticleResponseDto();
        modelMapper.map(requestDto, dto);
        dto.setArticleId(UUID.randomUUID().toString());
        return ResponseEntity.created(URI.create("/v1/article/" + dto.getArticleId())).body(dto);
    }

    @PutMapping("{articleId}")
    public ResponseEntity<?> update(@PathVariable String articleId, @RequestBody ArticleUpdateRequestDto requestDto) {
        // TODO : need article owner checking
        ArticleResponseDto dto = createDummyArticleResponseDto();
        modelMapper.map(requestDto, dto);
        dto.setArticleId(articleId);
        return ResponseEntity.ok(dto);
    }

    // TODO : Article 리스트 조회는 어떤 기준으로? 지역?
    // TODO : Article은 어떤 종류? 자유게시판? 공지사항?

    @DeleteMapping("{articleId}")
    public ResponseEntity<?> delete(@PathVariable String articleId){
        return ResponseEntity.ok().build();
    }

    @PostMapping("/like/{articleId}")
    public ResponseEntity<?> addLike(@PathVariable String articleId) {

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/like/{likeId}}")
    public ResponseEntity<?> deleteLike(@PathVariable String likeId) {
        return ResponseEntity.ok().build();
    }

    public static ArticleResponseDto createDummyArticleResponseDto() {
        return ArticleResponseDto.builder()
                .articleId(UUID.randomUUID().toString())
                .title("test-title")
                .content("test-content-with-html-format")
                .updatedTime(LocalDateTime.now())
                .likeCount(3)
                .comments(Collections.singletonList(CommentResponseDto.builder()
                        .memberId(UUID.randomUUID().toString())
                        .memberName("test-member-name")
                        .content("test-comment-content")
                        .updatedTime(LocalDateTime.now()).build())).build();
    }

}
