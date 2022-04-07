package com.bbolab.gaonna.api.security;

import com.bbolab.gaonna.api.security.exception.OAuth2ProviderNotMatchingException;
import com.bbolab.gaonna.core.domain.member.AuthProvider;
import com.bbolab.gaonna.api.security.provider.OAuth2UserInfo;
import com.bbolab.gaonna.api.security.provider.OAuth2UserInfoFactory;
import com.bbolab.gaonna.api.v1.controller.exception.OAuth2AuthenticationProcessingException;
import com.bbolab.gaonna.core.domain.member.Member;
import com.bbolab.gaonna.core.domain.member.Role;
import com.bbolab.gaonna.core.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        try {
            return processOAuth2User(userRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(userRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if(!StringUtils.hasText(userInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("OAuth2 공급자(네이버, 카카오) 에서 이메일을 찾을 수 없습니다.");
        }
        Optional<Member> memberOptional = memberRepository.findByEmail(userInfo.getEmail());
        Member member;
        if (memberOptional.isPresent()) {
            member = memberOptional.get();
            String registrationId = userRequest.getClientRegistration().getRegistrationId();
            if (!member.getProvider().equals(AuthProvider.valueOf(registrationId))) {
                String msg  = "소셜로그인 계정이 일치하지가 않습니다. 로그인 시도 {" + registrationId + "}, 계정과 연결된 Provider {" + member.getProvider() + "}";
                log.warn(msg);
                throw new OAuth2ProviderNotMatchingException(msg);
            }
            member = updateExistingMember(member, userInfo);
        }else {
            member = registerNewMember(userRequest, userInfo);
        }
        return UserPrincipal.create(member, oAuth2User.getAttributes());
    }

    private Member registerNewMember(OAuth2UserRequest userRequest, OAuth2UserInfo userInfo) {
        return memberRepository.save(Member.builder()
                .name(userInfo.getName())
                .email(userInfo.getEmail())
                .profileImage(userInfo.getImageUrl())
                .provider(AuthProvider.valueOf(userRequest.getClientRegistration().getRegistrationId()))
                .role(Role.USER)
                .build());
    }


    private Member updateExistingMember(Member member, OAuth2UserInfo oAuth2UserInfo) {
        return memberRepository.save(member.update(
                oAuth2UserInfo.getName(),
                oAuth2UserInfo.getImageUrl()
        ));
    }

}
