package com.bbolab.gaonna.api.util;

import com.bbolab.gaonna.core.domain.member.AuthProvider;
import com.bbolab.gaonna.core.domain.member.Member;
import com.bbolab.gaonna.core.domain.member.Role;

public class MemberFactory {

    public static Member createMemberStub(String name) {
        return Member.builder()
                .name(name)
                .nickname(name)
                .email(name + "@bbobbi.com")
                .provider(AuthProvider.kakao)
                .role(Role.ROLE_USER)
                .build();
    }

}
