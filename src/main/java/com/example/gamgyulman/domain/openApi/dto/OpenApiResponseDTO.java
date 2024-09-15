package com.example.gamgyulman.domain.openApi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

public class OpenApiResponseDTO {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OpenApiInfoDTO { // 초기 화면
        private String title;
        private String addr1;
        private String contentid;
        private String contenttypeid;
        private String firstimage;
        private String firstimage2;
        private double mapx;
        private double mapy;
        private String tel;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Setter
    public static class OpenApiInfoListDTO { // 초기 화면
        private List<OpenApiInfoDTO> item;
        private int numOfRows;
        private int pageNo;
        private int totalCount;
    }
}
