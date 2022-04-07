package com.bbolab.gaonna.api.v1.controller.security;

import com.bbolab.gaonna.api.security.CurrentUser;
import com.bbolab.gaonna.api.security.UserPrincipal;
import com.bbolab.gaonna.core.domain.member.Member;
import com.bbolab.gaonna.core.exception.ResourceNotFoundException;
import com.bbolab.gaonna.core.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TestController {

    private final MemberRepository memberRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public Member getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        Member member = memberRepository.findById(userPrincipal.getUuid())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getUuid()));
        return member;
    }
}
