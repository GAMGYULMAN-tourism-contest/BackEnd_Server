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
}
