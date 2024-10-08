package com.example.gamgyulman.domain.member.service;

import com.example.gamgyulman.domain.member.dto.MemberResponseDTO;
import com.example.gamgyulman.domain.member.dto.OAuth2DTO;
import com.example.gamgyulman.domain.member.entity.Member;
import com.example.gamgyulman.domain.member.entity.enums.Provider;
import com.example.gamgyulman.domain.member.exception.AuthErrorCode;
import com.example.gamgyulman.domain.member.exception.AuthException;
import com.example.gamgyulman.domain.member.jwt.JwtProvider;
import com.example.gamgyulman.domain.member.repository.MemberRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelOption;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.netty.http.client.HttpClient;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuth2ServiceImpl implements OAuth2Service{

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    @Value("${oauth2.google.client-id}")
    private String clientId;

    @Value("${oauth2.google.client-secret}")
    private String secret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectURI;

    @Override
    public MemberResponseDTO.OAuthResponseDTO login(String provider, String code) {
        if (provider.equalsIgnoreCase(Provider.GOOGLE.toString())) {
            return loginWithGoogle(code);
        }
        throw new AuthException(AuthErrorCode.UNSUPPORTED_PROVIDER);
    }

    // 구글 로그인 로직
    private MemberResponseDTO.OAuthResponseDTO loginWithGoogle(String code) {
        String token = getAccessTokenOnGoogle(code);
        OAuth2DTO.GoogleUserInfo userInfo = getUserInfoOnGoogle(token);

        Optional<Member> optional = memberRepository.findByEmail(userInfo.getEmail());

        Member member;
        if (optional.isPresent()) {
            member = optional.get();
            member.update(userInfo.getEmail(), userInfo.getName(), userInfo.getPicture());
        }
        else {
            member = memberRepository.save(Member.builder()
                    .email(userInfo.getEmail())
                    .name(userInfo.getName())
                    .image(userInfo.getPicture())
                    .provider(Provider.GOOGLE)
                    .build());
        }

        return MemberResponseDTO.OAuthResponseDTO.builder()
                .id(member.getId())
                .accessToken(jwtProvider.createAccessToken(member))
                .refreshToken(jwtProvider.createRefreshToken(member))
                .build();
    }

    private String getAccessTokenOnGoogle(String accessCode) {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://oauth2.googleapis.com")
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create()
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)))
                .build();
        String decode = URLDecoder.decode(accessCode, StandardCharsets.UTF_8);

        String response = webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/token")
                        .queryParam("code", decode)
                        .queryParam("client_id", clientId)
                        .queryParam("client_secret", secret)
                        .queryParam("redirect_uri", redirectURI)
                        .queryParam("grant_type", "authorization_code")
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return getAccessTokenFromResponseOnGoogle(response);
    }

    private String getAccessTokenFromResponseOnGoogle(String response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode node = objectMapper.readTree(response).path("access_token");
            return node.asText();
        } catch (Exception e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }
        return null;
    }

    private OAuth2DTO.GoogleUserInfo getUserInfoOnGoogle(String accessToken) {
        WebClient webClient = WebClient
                .builder()
                .baseUrl("https://www.googleapis.com")
                .build();

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("userinfo/v2/me")
                        .queryParam("access_token", accessToken)
                        .build())
                .retrieve()
                .bodyToMono(OAuth2DTO.GoogleUserInfo.class)
                .block();
    }

}
