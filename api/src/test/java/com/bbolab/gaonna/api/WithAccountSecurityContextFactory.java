package com.bbolab.gaonna.api;

import com.bbolab.gaonna.api.security.model.UserPrincipal;
import com.bbolab.gaonna.core.domain.member.AuthProvider;
import com.bbolab.gaonna.core.domain.member.Member;
import com.bbolab.gaonna.core.domain.member.Role;
import com.bbolab.gaonna.core.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

@RequiredArgsConstructor
public class WithAccountSecurityContextFactory implements WithSecurityContextFactory<WithAccount> {

    @Autowired
    MemberRepository memberRepository;

    @Override
    public SecurityContext createSecurityContext(WithAccount account) {
        String name = account.value();

        Member member = createMemberStub(name);
        memberRepository.save(member);

        UserPrincipal userDetails = UserPrincipal.create(member);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);
        return context;
    }

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
