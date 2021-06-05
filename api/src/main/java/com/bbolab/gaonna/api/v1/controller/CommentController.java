package com.bbolab.gaonna.api.v1.controller;

import com.bbolab.gaonna.api.v1.dto.comment.CommentCreateUpdateRequestDto;
import com.bbolab.gaonna.api.v1.dto.comment.CommentListResponseDto;
import com.bbolab.gaonna.api.v1.dto.comment.CommentResponseDto;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/v1/article/{articleId}/comment")
public class CommentController {

    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<?> getAll(@PathVariable String articleId) {
        CommentResponseDto comment = createDummyComment();
        CommentListResponseDto dto = CommentListResponseDto.builder().build();
        dto.setCommentLists(Collections.singletonList(comment));
        dto.setArticleId(articleId);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<?> createComment(@PathVariable String articleId, @RequestBody CommentCreateUpdateRequestDto requestDto) {
        CommentResponseDto dto = createDummyCommentResponseDto();
        modelMapper.map(requestDto, dto);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("{commentId}")
    public ResponseEntity<?>  updateComment(@PathVariable String articleId, @PathVariable String commentId, @RequestBody CommentCreateUpdateRequestDto requestDto) {
        CommentResponseDto dto = createDummyComment();
        modelMapper.map(requestDto, dto);
        dto.setCommentId(commentId);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable String articleId, @PathVariable String commentId) {
        return ResponseEntity.ok().build();
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
}
