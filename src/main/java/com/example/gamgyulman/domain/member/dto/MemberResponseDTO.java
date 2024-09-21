package com.example.gamgyulman.domain.member.dto;

import com.example.gamgyulman.domain.member.entity.Member;
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

        public static MemberInfoDTO from(Member member) {
            return MemberInfoDTO.builder()
                    .id(member.getId())
                    .email(member.getEmail())
                    .name(member.getName())
                    .image(member.getImage())
                    .build();
        }
    }
}
