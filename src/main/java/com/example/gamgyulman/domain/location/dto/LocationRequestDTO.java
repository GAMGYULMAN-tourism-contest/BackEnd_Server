package com.example.gamgyulman.domain.location.dto;

import com.example.gamgyulman.domain.location.entity.Location;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

public class LocationRequestDTO {

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class LocationRequestInfoDTO {
        @Schema(defaultValue = "3346982")
        private String contentId;
        @Schema(defaultValue = "76")
        private String contentTypeId;

        public Location toEntity() {
            return Location.builder()
                    .contentId(this.contentId)
                    .contentTypeId(this.contentTypeId)
                    .build();
        }
    }

}
