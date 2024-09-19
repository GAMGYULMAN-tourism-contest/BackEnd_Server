package com.example.gamgyulman.domain.location.dto;

import com.example.gamgyulman.domain.location.entity.Location;
import lombok.*;

public class LocationRequestDTO {

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class LocationRequestInfoDTO {
        private String contentId;
        private String contentTypeId;

        public Location toEntity() {
            return Location.builder()
                    .contentId(this.contentId)
                    .contentTypeId(this.contentTypeId)
                    .build();
        }
    }

}
