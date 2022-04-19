package com.bbolab.gaonna.api.security;

import com.bbolab.gaonna.api.security.model.UserPrincipal;
import com.bbolab.gaonna.core.domain.member.Member;
import com.bbolab.gaonna.core.exception.ResourceNotFoundException;
import com.bbolab.gaonna.core.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다.")
                );

        return UserPrincipal.create(member);
    }

    public UserDetails loadUserById(UUID userId) {
        Member member = memberRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("Member", "id", userId)
        );
        return UserPrincipal.create(member);
    }
}
