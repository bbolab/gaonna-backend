package com.bbolab.gaonna.api.v1.service;

import com.bbolab.gaonna.api.v1.dto.member.MemberInfoDto;

import java.util.UUID;

public interface MemberService {
    public MemberInfoDto getMemberInfo(UUID memberId);
}
