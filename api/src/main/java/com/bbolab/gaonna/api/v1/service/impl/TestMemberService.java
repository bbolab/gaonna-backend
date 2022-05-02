package com.bbolab.gaonna.api.v1.service.impl;

import com.bbolab.gaonna.api.v1.dto.member.MemberInfoDto;
import com.bbolab.gaonna.api.v1.service.MemberService;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.bbolab.gaonna.api.v1.controller.MockFactoryUtil.createDummyMemberInfoDto;

@Service
public class TestMemberService implements MemberService {
    @Override
    public MemberInfoDto getMemberInfo(UUID memberId) {
        return createDummyMemberInfoDto(memberId.toString());
    }
}
