package com.bbolab.gaonna.api.v1.controller;

import com.bbolab.gaonna.api.v1.dto.comment.CommentCreateUpdateRequestDto;
import com.bbolab.gaonna.api.v1.dto.comment.CommentListResponseDto;
import com.bbolab.gaonna.api.v1.dto.comment.CommentResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Collections;

import static com.bbolab.gaonna.api.v1.controller.MockFactoryUtil.createDummyComment;
import static com.bbolab.gaonna.api.v1.controller.MockFactoryUtil.createDummyCommentResponseDto;

@Api(value = "comment")
@Controller
@RequiredArgsConstructor
@RequestMapping("/v1/article/{articleId}/comment")
public class CommentController {

    private final ModelMapper modelMapper;

    @ApiOperation(value = "Searching article's comments")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = CommentListResponseDto.class)})
    @GetMapping
    public ResponseEntity<CommentListResponseDto> getAll(@ApiParam(value = "ex) 72f92a8b-1866-4f08-bdf1-5c4826d0378b", required = true) @PathVariable String articleId) {
        CommentResponseDto comment = createDummyComment();
        CommentListResponseDto dto = CommentListResponseDto.builder()
                .commentLists(Collections.singletonList(comment))
                .articleId(articleId)
                .build();
        return ResponseEntity.ok().body(dto);
    }

    @ApiOperation(value = "Update comment on article")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = CommentResponseDto.class)})
    @PutMapping("{commentId}")
    public ResponseEntity<CommentResponseDto>  updateComment(@PathVariable String articleId, @PathVariable String commentId, @RequestBody CommentCreateUpdateRequestDto requestDto) {
        CommentResponseDto dto = createDummyComment();
        modelMapper.map(requestDto, dto);
        dto.setCommentId(commentId);
        return ResponseEntity.ok().body(dto);
    }

    @ApiOperation(value = "Create comment on article")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = CommentResponseDto.class)})
    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable String articleId, @RequestBody CommentCreateUpdateRequestDto requestDto) {
        CommentResponseDto dto = createDummyCommentResponseDto();
        modelMapper.map(requestDto, dto);
        return ResponseEntity.ok().body(dto);
    }

    @ApiOperation(value = "Delete comment on article")
    @DeleteMapping("{commentId}")
    @ApiResponses({@ApiResponse(code = 200, message = "Success")})
    public ResponseEntity<Void> deleteComment(@PathVariable String articleId, @PathVariable String commentId) {
        return ResponseEntity.ok().build();
    }
}