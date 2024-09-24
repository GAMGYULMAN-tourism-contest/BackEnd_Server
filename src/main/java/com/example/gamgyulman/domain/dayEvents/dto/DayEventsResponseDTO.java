package com.example.gamgyulman.domain.dayEvents.dto;

import com.example.gamgyulman.domain.dayEvents.entity.DayEvents;
import com.example.gamgyulman.domain.event.dto.EventResponseDTO;
import lombok.*;

import java.time.LocalDate;
import java.util.Comparator;
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
        private List<EventResponseDTO.EventPreviewDTO> events;

        public static DayEventsInfoDTO from(DayEvents dayEvents) {
            return DayEventsInfoDTO.builder()
                    .id(dayEvents.getId())
                    .day(dayEvents.getDay())
                    .date(dayEvents.getDate())
                    .events(dayEvents.getEvents().stream().map(EventResponseDTO.EventPreviewDTO::from).sorted(Comparator.comparing(EventResponseDTO.EventPreviewDTO::getStartTime)).toList())
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
