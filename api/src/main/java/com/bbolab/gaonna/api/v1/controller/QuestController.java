package com.bbolab.gaonna.api.v1.controller;

import com.bbolab.gaonna.api.v1.dto.article.CommentDto;
import com.bbolab.gaonna.api.v1.dto.category.CategoryDto;
import com.bbolab.gaonna.api.v1.dto.quest.QuestCreateRequestDto;
import com.bbolab.gaonna.api.v1.dto.quest.QuestResponseDto;
import com.bbolab.gaonna.api.v1.dto.quest.QuestUpdateRequestDto;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/quest")
@RequiredArgsConstructor
public class QuestController {
    // TODO : Validator
    private final ModelMapper modelMapper;

    @GetMapping("{questId}")
    public ResponseEntity<?> get(@PathVariable String questId) {
        // find Quest by questId
        QuestResponseDto dto = createDummyQuestResponseDto();
        dto.setQuestId(questId);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping
    public ResponseEntity<?> list(@RequestParam double topLongitude, @RequestParam double topLatitude,
                                  @RequestParam double bottomLongitude, @RequestParam double bottomLatitude) {
        List<QuestResponseDto> dtos = Collections.singletonList(createDummyQuestResponseDto());
        for (QuestResponseDto dto : dtos) {
            dto.setLongitude(Math.abs(topLongitude - bottomLongitude) / 2);
            dto.setLatitude(Math.abs(topLatitude - bottomLatitude) / 2);
        }
        return ResponseEntity.ok().body(dtos);
    }

    // TODO : Create 성공 이후 반환 데이터 정의 필요 : created(201)랑 URI만 반환할지, 생성된 데이터 DTO도 함께 반환할지
    @PostMapping
    public ResponseEntity<?> create(@RequestBody QuestCreateRequestDto requestDto) {
//        QuestResponseDto dto = questService.createQuest(requestDto);
        QuestResponseDto dto = createDummyQuestResponseDto();
        modelMapper.map(requestDto, dto);
        return ResponseEntity.created(URI.create("/v1/quest/" + dto.getQuestId())).body(dto);
    }

    // TODO : Update 성공 이후 반환 데이터 정의 필요 : ok(200)만 반환할지, 수정된 데이터 DTO도 함께 반환할지
    @PutMapping
    public ResponseEntity<?> update(@RequestBody QuestUpdateRequestDto requestDto) {
        // TODO : need quest owner checking
        QuestResponseDto questResponseDto = createDummyQuestResponseDto();
        modelMapper.map(requestDto, questResponseDto);
        return ResponseEntity.ok().body(questResponseDto);
    }

    @DeleteMapping("{questId}")
    public ResponseEntity<?> delete() {
        return ResponseEntity.ok().build();
    }

    public static QuestResponseDto createDummyQuestResponseDto() {
        return QuestResponseDto.builder()
                .articleId(UUID.randomUUID().toString())
                .title("test-title")
                .content("test-content-with-html-format")
                .updatedTime(LocalDateTime.now())
                .likeCount(3)
                .comments(Collections.singletonList(CommentDto.builder()
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
