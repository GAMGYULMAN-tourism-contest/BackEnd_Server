package com.example.gamgyulman.domain.member.dto;

import lombok.*;

public class MemberResponseDTO {

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class OAuthResponseDTO {
        private Long id;
        private String accessToken;
        private String refreshToken;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MemberInfoDTO {
        private Long id;
        private String email;
        private String name;
        private String image;
    }
}
