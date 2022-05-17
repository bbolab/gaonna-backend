package com.bbolab.gaonna.api.v1.controller;

import com.bbolab.gaonna.api.security.model.CurrentUser;
import com.bbolab.gaonna.api.security.model.UserPrincipal;
import com.bbolab.gaonna.api.v1.dto.comment.CommentCreateRequestDto;
import com.bbolab.gaonna.api.v1.dto.comment.CommentListResponseDto;
import com.bbolab.gaonna.api.v1.dto.comment.CommentResponseDto;
import com.bbolab.gaonna.api.v1.dto.comment.CommentUpdateRequestDto;
import com.bbolab.gaonna.api.v1.service.CommentService;
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

import java.util.UUID;

@Api(value = "comment")
@Controller
@RequiredArgsConstructor
@RequestMapping("/v1/article/{articleId}/comment")
public class CommentController {

    private final ModelMapper modelMapper;
    private final CommentService commentService;

    @ApiOperation(value = "Searching article's comments")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = CommentListResponseDto.class)})
    @GetMapping
    public ResponseEntity<CommentListResponseDto> getAll(@CurrentUser UserPrincipal userPrincipal, @ApiParam(value = "ex) 72f92a8b-1866-4f08-bdf1-5c4826d0378b", required = true) @PathVariable String articleId) {
        CommentListResponseDto dto = commentService.getAllArticleComments(userPrincipal.getUuid(), UUID.fromString(articleId));
        return ResponseEntity.ok().body(dto);
    }

    @ApiOperation(value = "Update comment on article")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = CommentResponseDto.class)})
    @PutMapping("{commentId}")
    public ResponseEntity<CommentResponseDto>  updateComment(@CurrentUser UserPrincipal userPrincipal, @PathVariable String articleId, @PathVariable String commentId, @RequestBody CommentUpdateRequestDto requestDto) {
        CommentResponseDto dto = commentService.updateComment(userPrincipal.getUuid(), UUID.fromString(commentId), requestDto.getContent());
        return ResponseEntity.ok(dto);
    }

    @ApiOperation(value = "Create comment on article")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = CommentResponseDto.class)})
    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@CurrentUser UserPrincipal userPrincipal, @PathVariable String articleId, @RequestBody CommentCreateRequestDto requestDto) {
        CommentResponseDto dto = commentService.createComment(userPrincipal.getUuid(), UUID.fromString(articleId), UUID.fromString(requestDto.getParentId()), requestDto.getContent());
        return ResponseEntity.ok(dto);
    }

    @ApiOperation(value = "Delete comment on article")
    @DeleteMapping("{commentId}")
    @ApiResponses({@ApiResponse(code = 200, message = "Success")})
    public ResponseEntity<Void> deleteComment(@CurrentUser UserPrincipal userPrincipal, @PathVariable String articleId, @PathVariable String commentId) {
        commentService.deleteComment(userPrincipal.getUuid(), UUID.fromString(commentId));
        return ResponseEntity.ok().build();
    }
}
