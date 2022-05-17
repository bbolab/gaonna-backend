package com.bbolab.gaonna.api.v1.controller;

import com.bbolab.gaonna.api.security.model.CurrentUser;
import com.bbolab.gaonna.api.security.model.UserPrincipal;
import com.bbolab.gaonna.api.v1.dto.member.profile.ProfileCreateRequestDto;
import com.bbolab.gaonna.api.v1.dto.member.profile.ProfileDetailDto;
import com.bbolab.gaonna.api.v1.dto.member.profile.ProfileDetailListDto;
import com.bbolab.gaonna.api.v1.service.ProfileService;
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
import java.util.List;
import java.util.UUID;

import static com.bbolab.gaonna.api.v1.controller.MockFactoryUtil.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/profile")
public class ProfileController {

    private final ModelMapper modelMapper;
    private final ProfileService profileService;

    @ApiOperation(value = "Getting user's specific profile.")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = ProfileDetailDto.class)})
    @GetMapping("{profileId}")
    public ResponseEntity<ProfileDetailDto> get(@PathVariable String profileId) {
        ProfileDetailDto dto = profileService.getProfile(UUID.fromString(profileId));
        return ResponseEntity.ok(dto);
    }

    @ApiOperation(value = "Getting user's all profiles.")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = ProfileDetailListDto.class)})
    @GetMapping("/all")
    public ResponseEntity<ProfileDetailListDto> getAll(@CurrentUser UserPrincipal userPrincipal) {
        List<ProfileDetailDto> profiles = profileService.getProfiles(userPrincipal.getUuid());
        ProfileDetailListDto build = ProfileDetailListDto.builder()
            .profileCount(profiles.size())
            .profiles(profiles)
            .build();
        return ResponseEntity.ok().body(build);
    }

    @ApiOperation(value = "Create user's new profile.")
    @ApiResponses({@ApiResponse(code = 201, message = "Success")})
    @PostMapping
    public ResponseEntity<?> post(@CurrentUser UserPrincipal userPrincipal, @RequestBody ProfileCreateRequestDto requestDto) {
        ProfileDetailDto dto = profileService.createNewProfile(userPrincipal.getUuid(), requestDto);
        return ResponseEntity.created(URI.create("v1/profile/" + dto.getProfileId())).build();
    }

    @ApiOperation(value = "Update user's new profile.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 406, message = "Not acceptable. No permission to update the resource.")
    })
    @PutMapping("{profileId}")
    public ResponseEntity<?> put(@CurrentUser UserPrincipal userPrincipal, @PathVariable String profileId, @RequestBody ProfileCreateRequestDto requestDto) {
        ProfileDetailDto dto = profileService.updateProfile(userPrincipal.getUuid(), UUID.fromString(profileId), requestDto);
        return ResponseEntity.ok(dto);
    }

    @ApiOperation(value = "Delete user's specific profile.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 406, message = "Not acceptable. No permission to delete the resource.")
    })
    @DeleteMapping("{profileId}")
    public ResponseEntity<?> delete(@CurrentUser UserPrincipal userPrincipal, @PathVariable String profileId) {
        profileService.deleteProfile(userPrincipal.getUuid(), UUID.fromString(profileId));
        return ResponseEntity.ok().build();
    }
}
