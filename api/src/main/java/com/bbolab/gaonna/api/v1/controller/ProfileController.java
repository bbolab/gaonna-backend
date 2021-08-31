package com.bbolab.gaonna.api.v1.controller;

import com.bbolab.gaonna.api.v1.dto.member.profile.ProfileCreateRequestDto;
import com.bbolab.gaonna.api.v1.dto.member.profile.ProfileDetailDto;
import com.bbolab.gaonna.api.v1.dto.member.profile.ProfileDetailListDto;
import io.swagger.annotations.ApiOperation;
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
import java.util.Arrays;
import java.util.UUID;

import static com.bbolab.gaonna.api.v1.controller.MockFactoryUtil.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/profile")
public class ProfileController {

    private final ModelMapper modelMapper;

    @ApiOperation(value = "Getting user's specific profile.")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = ProfileDetailDto.class)})
    @GetMapping("{profileId}")
    public ResponseEntity<ProfileDetailDto> get(@PathVariable String profileId) {
        ProfileDetailDto dto = createDummyProfileDetailDto(profileId);
        return ResponseEntity.ok().body(dto);
    }

    @ApiOperation(value = "Getting user's all profiles.")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = ProfileDetailListDto.class)})
    @GetMapping("/all")
    public ResponseEntity<ProfileDetailListDto> getAll() {
        ProfileDetailDto profile1 = createDummyProfileDetailDto(UUID.randomUUID().toString());
        ProfileDetailDto profile2 = createDummyProfileDetailDto(UUID.randomUUID().toString());
        ProfileDetailListDto build = ProfileDetailListDto.builder()
                .profileCount(2)
                .profiles(Arrays.asList(profile1, profile2))
                .build();
        return ResponseEntity.ok().body(build);
    }

    @ApiOperation(value = "Create user's new profile.")
    @ApiResponses({@ApiResponse(code = 201, message = "Success")})
    @PostMapping
    public ResponseEntity<?> post(@RequestBody ProfileCreateRequestDto requestDto) {
        ProfileDetailDto dto = createDummyProfileDetailDto(UUID.randomUUID().toString());
        modelMapper.map(requestDto, dto);
        return ResponseEntity.created(URI.create("v1/profile/" + dto.getProfileId())).build();
    }

    @ApiOperation(value = "Update user's new profile.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 406, message = "Not acceptable. No permission to update the resource.")
    })
    @PutMapping("{profileId}")
    public ResponseEntity<?> put(@PathVariable String profileId, @RequestBody ProfileCreateRequestDto requestDto){
        ProfileDetailDto dto = createDummyProfileDetailDto(UUID.randomUUID().toString());
        modelMapper.map(requestDto, dto);
        dto.setProfileId(profileId);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Delete user's specific profile.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 406, message = "Not acceptable. No permission to delete the resource.")
    })
    @DeleteMapping("{profileId}")
    public ResponseEntity<?> delete(@PathVariable String profileId) {
        return ResponseEntity.ok().build();
    }
}
