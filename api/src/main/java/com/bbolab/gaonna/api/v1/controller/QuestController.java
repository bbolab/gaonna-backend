package com.bbolab.gaonna.api.v1.controller;

import com.bbolab.gaonna.api.v1.controller.validator.BboxConstraint;
import com.bbolab.gaonna.api.v1.controller.validator.BboxConstraintValidator;
import com.bbolab.gaonna.api.v1.dto.quest.QuestCreateUpdateRequestDto;
import com.bbolab.gaonna.api.v1.dto.quest.QuestDetailResponseDto;
import com.bbolab.gaonna.api.v1.dto.quest.QuestListResponseItemDto;
import com.bbolab.gaonna.api.v1.dto.quest.QuestListResponseDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Arrays;

import static com.bbolab.gaonna.api.v1.controller.MockFactoryUtil.createDummyQuestListItemDto;
import static com.bbolab.gaonna.api.v1.controller.MockFactoryUtil.createDummyQuestResponseDto;

@Validated
@RestController
@RequestMapping("/v1/quest")
@RequiredArgsConstructor
public class QuestController {
    private final ModelMapper modelMapper;

    @ApiOperation(value = "Searching quest with Quest UUID")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = QuestDetailResponseDto.class)})
    @GetMapping("/detail/{questId}")
    public ResponseEntity<QuestDetailResponseDto> get(@ApiParam(value = "ex) 72f92a8b-1866-4f08-bdf1-5c4826d0378b", required = true) @PathVariable String questId) {
        // find Quest by questId
        QuestDetailResponseDto dto = createDummyQuestResponseDto();
        dto.setQuestId(questId);
        return ResponseEntity.ok().body(dto);
    }

    @ApiOperation(value = "Searching quest", notes = "Search for quests inside a selected area on the map. You should pass bbox coordinates for searching.")
    @ApiImplicitParam(name = "bbox", required = true, paramType = "query", value = "선택 영역 좌표 - [[좌하단경도,좌하단위도],[우상단경도,우상단위도]]", example = "[[127.0403165,37.2746168],[127.04645333,37.2796836]]")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = QuestListResponseDto.class)})
    @GetMapping("/list")
    public ResponseEntity<QuestListResponseDto> list(@BboxConstraint String bbox) {
        Double[][] bboxArr = BboxConstraintValidator.parseBboxStringToDoubleArray(bbox);
        Double minX = bboxArr[0][0];
        Double minY = bboxArr[0][1];
        Double maxX = bboxArr[1][0];
        Double maxY = bboxArr[1][1];

        QuestListResponseItemDto info1 = modelMapper.map(createDummyQuestListItemDto(), QuestListResponseItemDto.class);
        QuestListResponseItemDto info2 = modelMapper.map(createDummyQuestListItemDto(), QuestListResponseItemDto.class);

        QuestListResponseDto dto = QuestListResponseDto.builder().build();
        dto.setN(2);
        dto.setQuests(Arrays.asList(info1, info2));
        return ResponseEntity.ok().body(dto);
    }

    @ApiOperation(value = "Create new quest")
    @ApiResponses({@ApiResponse(code = 201, message = "Success", response = QuestDetailResponseDto.class)})
    @PostMapping
    public ResponseEntity<QuestDetailResponseDto> create(@RequestBody QuestCreateUpdateRequestDto requestDto) {
//        QuestResponseDto dto = questService.createQuest(requestDto);
        QuestDetailResponseDto dto = createDummyQuestResponseDto();
        modelMapper.map(requestDto, dto);
        return ResponseEntity.created(URI.create("/v1/quest/" + dto.getQuestId())).body(dto);
    }

    @ApiOperation(value = "Update quest")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = QuestDetailResponseDto.class)})
    @PutMapping("{questId}")
    public ResponseEntity<QuestDetailResponseDto> update(@PathVariable String questId, @RequestBody QuestCreateUpdateRequestDto requestDto) {
        // TODO : need quest owner checking
        QuestDetailResponseDto questDetailResponseDto = createDummyQuestResponseDto();
        modelMapper.map(requestDto, questDetailResponseDto);
        questDetailResponseDto.setQuestId(questId);
        return ResponseEntity.ok().body(questDetailResponseDto);
    }

    @ApiOperation(value = "Delete quest")
    @ApiResponses({@ApiResponse(code = 200, message = "Success")})
    @DeleteMapping("{questId}")
    public ResponseEntity<Void> delete(@PathVariable String questId) {
        return ResponseEntity.ok().build();
    }

}
