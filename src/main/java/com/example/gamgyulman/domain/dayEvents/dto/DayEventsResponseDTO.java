package com.example.gamgyulman.domain.dayEvents.dto;

import com.example.gamgyulman.domain.dayEvents.entity.DayEvents;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

public class DayEventsResponseDTO {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class DayEventsInfoDTO {
        private Long id;
        private int day;
        private LocalDate date;

        public static DayEventsInfoDTO from(DayEvents dayEvents) {
            return DayEventsInfoDTO.builder()
                    .id(dayEvents.getId())
                    .day(dayEvents.getDay())
                    .date(dayEvents.getDate())
                    .build();
        }
    }


    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class DayEventsInfoListDTO {
        private List<DayEventsInfoDTO> dayEventsList;

        public static DayEventsInfoListDTO from(List<DayEvents> dayEvents) {
            return DayEventsInfoListDTO.builder()
                    .dayEventsList(dayEvents.stream().map(DayEventsInfoDTO::from).toList())
                    .build();
        }
    }
}
