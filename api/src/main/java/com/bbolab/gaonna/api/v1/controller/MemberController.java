package com.bbolab.gaonna.api.v1.controller;

import com.bbolab.gaonna.api.v1.dto.member.MemberInfoDto;
import com.bbolab.gaonna.api.v1.service.MemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/member")
public class MemberController {

    private final MemberService memberService;

    @ApiOperation(value = "Getting User's information", notes = "(doing quests, requested quests, profile list, recent review, total score)")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = MemberInfoDto.class)})
    @GetMapping("info/{memberId}")
    public ResponseEntity<MemberInfoDto> getUserInfo(@PathVariable String memberId) {
        MemberInfoDto dto = memberService.getMemberInfo(UUID.fromString(memberId));
        return ResponseEntity.ok(dto);
    }
}
