package com.example.gamgyulman.domain.openApi.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

public class TravelInfoResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class DefaultTravelInfoDTO {
        private String title;
        private String address;
        private String contentId;
        private String contentTypeId;
        private List<String> images;
        private double mapx;
        private double mapy;
        private String tel;

        public static DefaultTravelInfoDTO from(OpenApiResponseDTO.OpenApiInfoDTO dto) {
            List<String> imageList= new ArrayList<>();
            if (dto.getFirstimage() != null && !dto.getFirstimage().isEmpty()) {
                imageList.add(dto.getFirstimage());
                if (dto.getFirstimage2() != null && !dto.getFirstimage2().isEmpty()) {
                    imageList.add(dto.getFirstimage2());
                }
            }
            return DefaultTravelInfoDTO.builder()
                    .title(dto.getTitle())
                    .address(dto.getAddr1())
                    .contentId(dto.getContentid())
                    .contentTypeId(dto.getContenttypeid())
                    .images(imageList)
                    .mapx(dto.getMapx())
                    .mapy(dto.getMapy())
                    .tel(dto.getTel())
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class DefaultTravelInfoListDTO {
        private List<DefaultTravelInfoDTO> item;
        private int numOfRows;
        private int pageNo;
        private int totalCount;

        public static DefaultTravelInfoListDTO from(OpenApiResponseDTO.OpenApiInfoListDTO dto) {
            List<DefaultTravelInfoDTO> list = null;
            if (dto.getItem() != null) {
                list = dto.getItem().stream().map(DefaultTravelInfoDTO::from).toList();
            }
            return DefaultTravelInfoListDTO.builder()
                    .item(list)
                    .numOfRows(dto.getNumOfRows())
                    .pageNo(dto.getPageNo())
                    .totalCount(dto.getTotalCount())
                    .build();

        }
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class TravelDetailInfoDTO {
        private String title;
        private String overview;
        private String contentId;
        private String contentTypeId;
        private String homepage;
        private List<String> images;
        private String address;
        private double mapx;
        private double mapy;
        private String tel;

        public static TravelDetailInfoDTO from(OpenApiResponseDTO.OpenApiDetailDTO dto) {
            List<String> imageList= new ArrayList<>();
            if (dto.getFirstimage() != null && !dto.getFirstimage().isEmpty()) {
                imageList.add(dto.getFirstimage());
                if (dto.getFirstimage2() != null && !dto.getFirstimage2().isEmpty()) {
                    imageList.add(dto.getFirstimage2());
                }
            }
            return TravelDetailInfoDTO.builder()
                    .title(dto.getTitle())
                    .overview(dto.getOverview())
                    .contentId(dto.getContentid())
                    .contentTypeId(dto.getContenttypeid())
                    .homepage(dto.getHomepage())
                    .images(imageList)
                    .address(dto.getAddr1())
                    .mapx(dto.getMapx())
                    .mapy(dto.getMapy())
                    .tel(dto.getTel())
                    .build();

        }
    }
}
