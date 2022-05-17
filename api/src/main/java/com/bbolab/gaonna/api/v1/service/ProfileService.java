package com.bbolab.gaonna.api.v1.service;

import com.bbolab.gaonna.api.v1.dto.member.profile.ProfileCreateRequestDto;
import com.bbolab.gaonna.api.v1.dto.member.profile.ProfileDetailDto;

import java.util.List;
import java.util.UUID;

public interface ProfileService {

    public ProfileDetailDto getProfile(UUID profileId);

    public List<ProfileDetailDto> getProfiles(UUID memberId);

    public ProfileDetailDto createNewProfile(UUID memberId, ProfileCreateRequestDto profileInfo);

    public ProfileDetailDto updateProfile(UUID memberId, UUID profileId, ProfileCreateRequestDto profileInfo);

    public boolean deleteProfile(UUID memberId, UUID profileId);
}