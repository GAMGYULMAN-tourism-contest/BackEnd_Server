package com.example.gamgyulman.domain.member.handler;

import com.example.gamgyulman.domain.member.entity.Member;
import com.example.gamgyulman.domain.member.exception.MemberErrorCode;
import com.example.gamgyulman.domain.member.exception.MemberException;
import com.example.gamgyulman.domain.member.jwt.JwtProvider;
import com.example.gamgyulman.domain.member.repository.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomSuccessfulHandler implements AuthenticationSuccessHandler {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    @Value("${Jwt.oauth.redirect-uri}")
    private final String redirectUri;

    public CustomSuccessfulHandler(MemberRepository memberRepository, JwtProvider jwtProvider, @Value("${Jwt.oauth.redirect-uri}") String redirectUri) {
        this.memberRepository = memberRepository;
        this.jwtProvider = jwtProvider;
        this.redirectUri = redirectUri;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String email = ((OAuth2User) authentication.getPrincipal()).getAttribute("email");
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND));

        String accessToken = jwtProvider.createAccessToken(member);
        String refreshToken = jwtProvider.createRefreshToken(member);

        Cookie access = new Cookie("accessToken", accessToken);
        Cookie refresh = new Cookie("refreshToken", refreshToken);

        response.addCookie(access);
        response.addCookie(refresh);

        response.sendRedirect(redirectUri);
    }
}
