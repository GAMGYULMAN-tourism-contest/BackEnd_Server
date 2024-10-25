package com.example.gamgyulman.domain.location.dto;

import com.example.gamgyulman.domain.location.entity.Recommend;
import lombok.*;

public class RecommendResponseDTO {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class RecommendInfoDTO {
        private String title;
        private double mapx;
        private double mapy;
        private String contentId;

        public static RecommendInfoDTO from(Recommend recommend) {
            return RecommendInfoDTO.builder()
                    .title(recommend.getTitle())
                    .mapx(recommend.getMapx())
                    .mapy(recommend.getMapy())
                    .contentId(recommend.getContentId())
                    .build();
        }

    }

}
