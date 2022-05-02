package com.bbolab.gaonna.api.v1.controller;

import com.bbolab.gaonna.api.security.model.CurrentUser;
import com.bbolab.gaonna.api.security.model.UserPrincipal;
import com.bbolab.gaonna.api.v1.controller.validator.BboxConstraint;
import com.bbolab.gaonna.api.v1.dto.quest.QuestRequestDto;
import com.bbolab.gaonna.api.v1.dto.quest.QuestDetailResponseDto;
import com.bbolab.gaonna.api.v1.dto.quest.QuestListResponseItemDto;
import com.bbolab.gaonna.api.v1.dto.quest.QuestListResponseDto;
import com.bbolab.gaonna.api.v1.service.QuestService;
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
import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/v1/quest")
@RequiredArgsConstructor
public class QuestController {
    private final ModelMapper modelMapper;
    private final QuestService questService;

    @ApiOperation(value = "Searching quest with Quest UUID")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = QuestDetailResponseDto.class)})
    @GetMapping("/detail/{questId}")
    public ResponseEntity<QuestDetailResponseDto> get(@CurrentUser UserPrincipal userPrincipal, @ApiParam(value = "ex) 72f92a8b-1866-4f08-bdf1-5c4826d0378b", required = true) @PathVariable String questId) {
        QuestDetailResponseDto dto = questService.getQuestDetail(userPrincipal.getUuid(), UUID.fromString(questId));
        return ResponseEntity.ok().body(dto);
    }

    @ApiOperation(value = "Searching quest", notes = "Search for quests inside a selected area on the map. You should pass bbox coordinates for searching.")
    @ApiImplicitParam(name = "bbox", required = true, paramType = "query", value = "선택 영역 좌표 - [[좌하단경도,좌하단위도],[우상단경도,우상단위도]]", example = "[[127.0403165,37.2746168],[127.04645333,37.2796836]]")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = QuestListResponseDto.class)})
    @GetMapping("/list")
    public ResponseEntity<QuestListResponseDto> list(@CurrentUser UserPrincipal userPrincipal, @BboxConstraint String bbox) {
        List<QuestListResponseItemDto> quests = questService.getQuestsInBboxString(userPrincipal.getUuid(), bbox);

        QuestListResponseDto dto = QuestListResponseDto.builder()
                .n(quests.size())
                .quests(quests).build();

        return ResponseEntity.ok().body(dto);
    }

    @ApiOperation(value = "Create new quest")
    @ApiResponses({@ApiResponse(code = 201, message = "Success", response = QuestDetailResponseDto.class)})
    @PostMapping
    public ResponseEntity<QuestDetailResponseDto> create(@CurrentUser UserPrincipal userPrincipal, @RequestBody QuestRequestDto requestDto) {
        QuestDetailResponseDto dto = questService.createQuest(userPrincipal.getUuid(), requestDto);
        return ResponseEntity.created(URI.create("/v1/quest/" + dto.getQuestId())).body(dto);
    }

    @ApiOperation(value = "Update quest")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = QuestDetailResponseDto.class)})
    @PutMapping("{questId}")
    public ResponseEntity<QuestDetailResponseDto> update(@CurrentUser UserPrincipal userPrincipal, @PathVariable String questId, @RequestBody QuestRequestDto requestDto) {
        QuestDetailResponseDto dto = questService.updateQuest(userPrincipal.getUuid(), UUID.fromString(questId), requestDto);
        return ResponseEntity.ok(dto);
    }

    @ApiOperation(value = "Delete quest")
    @ApiResponses({@ApiResponse(code = 200, message = "Success")})
    @DeleteMapping("{questId}")
    public ResponseEntity<Void> delete(@CurrentUser UserPrincipal userPrincipal, @PathVariable String questId) {
        boolean result = questService.deleteQuest(userPrincipal.getUuid(), UUID.fromString(questId));
        return ResponseEntity.ok().build();
    }

}
