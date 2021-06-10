package com.bbolab.gaonna.api.v1.controller;

import com.bbolab.gaonna.api.v1.dto.article.ArticleCreateRequestDto;
import com.bbolab.gaonna.api.v1.dto.article.ArticleResponseDto;
import com.bbolab.gaonna.api.v1.dto.article.ArticleUpdateRequestDto;
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

import static com.bbolab.gaonna.api.v1.controller.MockFactoryUtil.createDummyArticleResponseDto;

@Api(value = "article")
@RestController
@RequestMapping("/v1/article")
@RequiredArgsConstructor
public class ArticleController {
    // Quest가 없는 일반 게시물을 조회하는 경우
    private final ModelMapper modelMapper;

    @ApiOperation(value = "Searching article")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = ArticleResponseDto.class)})
    @GetMapping("{articleId}")
    public ResponseEntity<ArticleResponseDto> get(@ApiParam(value = "ex) 72f92a8b-1866-4f08-bdf1-5c4826d0378b", required = true) @PathVariable String articleId) {
        ArticleResponseDto dto = createDummyArticleResponseDto();
        dto.setArticleId(articleId);
        return ResponseEntity.ok(dto);
    }

    @ApiOperation(value = "Create new article")
    @ApiResponses({@ApiResponse(code = 201, message = "Success", response = ArticleResponseDto.class)})
    @PostMapping
    public ResponseEntity<ArticleResponseDto> create(@RequestBody ArticleCreateRequestDto requestDto) {
        ArticleResponseDto dto = createDummyArticleResponseDto();
        modelMapper.map(requestDto, dto);
        dto.setArticleId(UUID.randomUUID().toString());
        return ResponseEntity.created(URI.create("/v1/article/" + dto.getArticleId())).body(dto);
    }

    @ApiOperation(value = "Update article")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = ArticleResponseDto.class)})
    @PutMapping("{articleId}")
    public ResponseEntity<ArticleResponseDto> update(@PathVariable String articleId, @RequestBody ArticleUpdateRequestDto requestDto) {
        // TODO : need article owner checking
        ArticleResponseDto dto = createDummyArticleResponseDto();
        modelMapper.map(requestDto, dto);
        dto.setArticleId(articleId);
        return ResponseEntity.ok(dto);
    }

    // TODO : Article 리스트 조회는 어떤 기준으로? 지역?
    // TODO : Article은 어떤 종류? 자유게시판? 공지사항?

    @ApiOperation(value = "Delete article")
    @ApiResponses({@ApiResponse(code = 200, message = "Success")})
    @DeleteMapping("{articleId}")
    public ResponseEntity<Void> delete(@PathVariable String articleId){
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "User add like to article")
    @ApiResponses({@ApiResponse(code = 200, message = "Success")})
    @PostMapping("/like/{articleId}")
    public ResponseEntity<Void> addLike(@PathVariable String articleId) {
        // TODO : 해당 요청을 한 유저가 Article에 좋아요를 이미 눌렀을 경우는 예외
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "User cancel like to article")
    @ApiResponses({@ApiResponse(code = 200, message = "Success")})
    @DeleteMapping("/like/{articleId}")
    public ResponseEntity<Void> deleteLike(@PathVariable String articleId) {
        // TODO : 해당 요청을 한 유저가 Article에 좋아요를 누르지 않았을 경우는 예외
        return ResponseEntity.ok().build();
    }
}
