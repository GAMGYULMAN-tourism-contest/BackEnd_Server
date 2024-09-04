package com.example.gamgyulman.domain.member.dto;

import lombok.*;

public class OAuth2DTO {

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class GoogleUserInfo {

        private String email;
        private String name;
        private String picture;
    }
}
