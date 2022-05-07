package com.bbolab.gaonna.api.v1.controller;

import com.bbolab.gaonna.api.security.model.CurrentUser;
import com.bbolab.gaonna.api.security.model.UserPrincipal;
import com.bbolab.gaonna.api.v1.dto.article.ArticleCreateRequestDto;
import com.bbolab.gaonna.api.v1.dto.article.ArticleResponseDto;
import com.bbolab.gaonna.api.v1.dto.article.ArticleUpdateRequestDto;
import com.bbolab.gaonna.api.v1.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
import java.util.UUID;

@Api(value = "article")
@RestController
@RequestMapping("/v1/article")
@RequiredArgsConstructor
public class ArticleController {
    // Quest가 없는 일반 게시물을 조회하는 경우
    private final ModelMapper modelMapper;
    private final ArticleService articleService;

    @ApiOperation(value = "Searching article")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = ArticleResponseDto.class)})
    @GetMapping("{articleId}")
    public ResponseEntity<ArticleResponseDto> get(@CurrentUser UserPrincipal userPrincipal, @ApiParam(value = "ex) 72f92a8b-1866-4f08-bdf1-5c4826d0378b", required = true) @PathVariable String articleId) {
        ArticleResponseDto dto = articleService.searchArticleById(UUID.fromString(articleId));
        return ResponseEntity.ok(dto);
    }

    @ApiOperation(value = "Create new article")
    @ApiResponses({@ApiResponse(code = 201, message = "Success", response = ArticleResponseDto.class)})
    @PostMapping
    public ResponseEntity<ArticleResponseDto> create(@CurrentUser UserPrincipal userPrincipal, @RequestBody ArticleCreateRequestDto requestDto) {
        ArticleResponseDto dto = articleService.createNewArticle(userPrincipal.getUuid(), requestDto);
        return ResponseEntity.created(URI.create("/v1/article/" + dto.getArticleId())).body(dto);
    }

    @ApiOperation(value = "Update article")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = ArticleResponseDto.class)})
    @PutMapping("{articleId}")
    public ResponseEntity<ArticleResponseDto> update(@CurrentUser UserPrincipal userPrincipal, @PathVariable String articleId, @RequestBody ArticleUpdateRequestDto requestDto) {
        ArticleResponseDto dto = articleService.updateArticleById(userPrincipal.getUuid(), UUID.fromString(articleId), requestDto);
        return ResponseEntity.ok(dto);
    }

    // TODO : Article 리스트 조회는 어떤 기준으로? 지역?
    // TODO : Article은 어떤 종류? 자유게시판? 공지사항?
    @ApiOperation(value = "Delete article")
    @ApiResponses({@ApiResponse(code = 200, message = "Success")})
    @DeleteMapping("{articleId}")
    public ResponseEntity<Void> delete(@CurrentUser UserPrincipal userPrincipal, @PathVariable String articleId){
        articleService.deleteArticleById(userPrincipal.getUuid(), UUID.fromString(articleId));
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "User add like to article")
    @ApiResponses({@ApiResponse(code = 200, message = "Success")})
    @PostMapping("/like/{articleId}")
    public ResponseEntity<Void> addLike(@CurrentUser UserPrincipal userPrincipal, @PathVariable String articleId) {
        articleService.addLikeToArticle(userPrincipal.getUuid(), UUID.fromString(articleId));
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "User cancel like to article")
    @ApiResponses({@ApiResponse(code = 200, message = "Success")})
    @DeleteMapping("/like/{articleId}")
    public ResponseEntity<Void> deleteLike(@CurrentUser UserPrincipal userPrincipal, @PathVariable String articleId) {
        articleService.deleteArticleById(userPrincipal.getUuid(), UUID.fromString(articleId));
        return ResponseEntity.ok().build();
    }
}
