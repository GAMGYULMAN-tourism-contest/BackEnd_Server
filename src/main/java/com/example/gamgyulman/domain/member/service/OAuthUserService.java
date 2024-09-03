package com.example.gamgyulman.domain.member.service;

import com.example.gamgyulman.domain.member.entity.Member;
import com.example.gamgyulman.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuthUserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getName();
        String image = oAuth2User.getAttribute("picture");

        memberRepository.findByEmail(email).ifPresentOrElse(
                member -> member.update(email, name, image),
                () -> {
                    Member member = Member.builder()
                            .email(email)
                            .name(name)
                            .image(image)
                            .build();
                    memberRepository.save(member);
                }
        );

        return oAuth2User;
    }
}
