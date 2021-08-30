package com.bbolab.gaonna.api.v1.controller;

import com.bbolab.gaonna.api.v1.dto.reserve.ReserveQuestRequestDto;
import com.bbolab.gaonna.api.v1.dto.reserve.ReserveQuestResponseDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

import static com.bbolab.gaonna.api.v1.controller.MockFactoryUtil.*;

@Validated
@RestController
@RequestMapping("/v1/reserve/quest")
@RequiredArgsConstructor
public class QuestReserveController {

    @ApiOperation(value = "Get reservation status through reservation Id")
    @GetMapping("{questId}/{reserveId}")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = ReserveQuestResponseDto.class)})
    public ResponseEntity<ReserveQuestResponseDto> get(@PathVariable String questId, @PathVariable String reserveId) {
        ReserveQuestResponseDto dto = createDummyReserveQuestResponseDto();
        dto.setQuestId(questId);
        dto.setReserveId(reserveId);
        return ResponseEntity.ok().body(dto);
    }

    @ApiOperation(value = "Add reservation request to the quest. Reservation completed if the requester accept the requests.")
    @ApiResponses({@ApiResponse(code = 201, message = "Success", response = ReserveQuestResponseDto.class)})
    @PostMapping("{questId}")
    public ResponseEntity<ReserveQuestResponseDto> reservate(@PathVariable String questId, @RequestBody ReserveQuestRequestDto profileId){
        // TODO : 1. check if profile Id belongs to requested member
        // TODO : 2. check if member already requested reservation as performer to the {quest Id}
        // TODO :   2.1 drop request if member already requested reservation to the {quest Id}
        // TODO :   2.2 add MemberQuestPerformer if member never requested the reservation to the {quest Id}

        ReserveQuestResponseDto dto = createDummyReserveQuestResponseDto();
        dto.setQuestId(questId);
        dto.setProfileId(profileId.getProfileId());
        String memberPerformerId = UUID.randomUUID().toString();
        return ResponseEntity.created(URI.create("/v1/reserve/quest/" + questId + "/" + dto.getReserveId())).body(dto);
    }

    @ApiOperation(value = "Cancel the reservation request. If reservation is accepted from the requester, the request fails.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 406, message = "Cancel request not acceptable.")
    })
    @DeleteMapping("{questId}/{reserveId}")
    public ResponseEntity<?> cancel(@PathVariable String questId, @PathVariable String reserveId) {
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Accept the reservation request.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 406, message = "Accept request not acceptable.")
    })
    @PostMapping("accept/{reserveId}")
    public ResponseEntity<?> accept(@PathVariable String reserveId) {
        return ResponseEntity.ok().build();
    }
}
