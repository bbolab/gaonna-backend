package com.bbolab.gaonna.api.v1.controller;

import com.bbolab.gaonna.api.v1.dto.comment.CommentResponseDto;
import com.bbolab.gaonna.api.v1.dto.category.CategoryDto;
import com.bbolab.gaonna.api.v1.dto.quest.QuestCreateUpdateRequestDto;
import com.bbolab.gaonna.api.v1.dto.quest.QuestDetailResponseDto;
import com.bbolab.gaonna.api.v1.dto.quest.QuestListResponseItemDto;
import com.bbolab.gaonna.api.v1.dto.quest.QuestListResponseDto;
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
        QuestDetailResponseDto dto = createDummyQuestResponseDto();
        dto.setQuestId(questId);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping
    public ResponseEntity<?> list(@RequestParam double topLongitude, @RequestParam double topLatitude,
                                  @RequestParam double bottomLongitude, @RequestParam double bottomLatitude) {
        QuestListResponseItemDto info = modelMapper.map(createDummyQuestResponseDto(), QuestListResponseItemDto.class);

        QuestListResponseDto dto = QuestListResponseDto.builder().build();
        dto.setQuests(Collections.singletonList(info));
        dto.getQuests().forEach(d -> {
            d.setLongitude(Math.abs(topLongitude - bottomLongitude) / 2);
            d.setLatitude(Math.abs(topLatitude - bottomLatitude) / 2);
        });
        return ResponseEntity.ok().body(dto);
    }

    // TODO : Create 성공 이후 반환 데이터 정의 필요 : created(201)랑 URI만 반환할지, 생성된 데이터 DTO도 함께 반환할지
    @PostMapping
    public ResponseEntity<?> create(@RequestBody QuestCreateUpdateRequestDto requestDto) {
//        QuestResponseDto dto = questService.createQuest(requestDto);
        QuestDetailResponseDto dto = createDummyQuestResponseDto();
        modelMapper.map(requestDto, dto);
        return ResponseEntity.created(URI.create("/v1/quest/" + dto.getQuestId())).body(dto);
    }

    // TODO : Update 성공 이후 반환 데이터 정의 필요 : ok(200)만 반환할지, 수정된 데이터 DTO도 함께 반환할지
    @PutMapping("{questId}")
    public ResponseEntity<?> update(@PathVariable String questId, @RequestBody QuestCreateUpdateRequestDto requestDto) {
        // TODO : need quest owner checking
        QuestDetailResponseDto questDetailResponseDto = createDummyQuestResponseDto();
        modelMapper.map(requestDto, questDetailResponseDto);
        questDetailResponseDto.setQuestId(questId);
        return ResponseEntity.ok().body(questDetailResponseDto);
    }

    @DeleteMapping("{questId}")
    public ResponseEntity<?> delete() {
        return ResponseEntity.ok().build();
    }

    public static QuestDetailResponseDto createDummyQuestResponseDto() {
        return QuestDetailResponseDto.builder()
                .articleId(UUID.randomUUID().toString())
                .title("test-title")
                .content("test-content-with-html-format")
                .updatedTime(LocalDateTime.now())
                .likeCount(3)
                .comments(Collections.singletonList(CommentResponseDto.builder()
                        .commentId(UUID.randomUUID().toString())
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
