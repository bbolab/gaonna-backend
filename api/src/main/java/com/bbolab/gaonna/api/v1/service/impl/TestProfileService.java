package com.bbolab.gaonna.api.v1.service.impl;

import com.bbolab.gaonna.api.v1.dto.member.profile.ProfileCreateRequestDto;
import com.bbolab.gaonna.api.v1.dto.member.profile.ProfileDetailDto;
import com.bbolab.gaonna.api.v1.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static com.bbolab.gaonna.api.v1.controller.MockFactoryUtil.createDummyProfileDetailDto;

@Service
@RequiredArgsConstructor
public class TestProfileService implements ProfileService {

    private final ModelMapper modelMapper;

    @Override
    public ProfileDetailDto getProfile(UUID profileId) {
        ProfileDetailDto dto = createDummyProfileDetailDto(profileId.toString());
        return dto;
    }

    @Override
    public List<ProfileDetailDto> getProfiles(UUID memberId) {
        ProfileDetailDto profile1 = createDummyProfileDetailDto(UUID.randomUUID().toString());
        ProfileDetailDto profile2 = createDummyProfileDetailDto(UUID.randomUUID().toString());
        profile1.getMember().setMemberId(memberId.toString());
        profile2.getMember().setMemberId(memberId.toString());
        List<ProfileDetailDto> profiles = new LinkedList<>();
        profiles.add(profile1);
        profiles.add(profile2);

        return profiles;
    }

    @Override
    public ProfileDetailDto createNewProfile(UUID memberId, ProfileCreateRequestDto profileInfo) {
        ProfileDetailDto dto = createDummyProfileDetailDto(UUID.randomUUID().toString());
        modelMapper.map(profileInfo, dto);
        return dto;
    }

    @Override
    public ProfileDetailDto updateProfile(UUID memberId, UUID profileId, ProfileCreateRequestDto profileInfo) {
        ProfileDetailDto dto = createDummyProfileDetailDto(UUID.randomUUID().toString());
        modelMapper.map(profileInfo, dto);
        dto.getMember().setMemberId(memberId.toString());
        dto.setProfileId(profileId.toString());
        return dto;
    }

    @Override
    public boolean deleteProfile(UUID memberId, UUID profileId) {
        return true;
    }
}
