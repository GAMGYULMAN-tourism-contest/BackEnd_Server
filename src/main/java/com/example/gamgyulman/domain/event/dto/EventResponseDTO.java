package com.example.gamgyulman.domain.event.dto;

import com.example.gamgyulman.domain.dayEvents.dto.DayEventsResponseDTO;
import com.example.gamgyulman.domain.event.entity.Event;
import com.example.gamgyulman.domain.openApi.dto.TravelInfoResponseDTO;
import lombok.*;

public class EventResponseDTO {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class EventInfoDTO {
        private Long id;
        private String title;
        private String description;
        private String startTime;
        private String endTime;
        private TravelInfoResponseDTO.TravelDetailInfoDTO location;
        private DayEventsResponseDTO.DayEventsInfoDTO dayEvents;

        public static EventInfoDTO from(Event event, TravelInfoResponseDTO.TravelDetailInfoDTO location) {
            return EventInfoDTO.builder()
                    .id(event.getId())
                    .title(event.getTitle())
                    .description(event.getDescription())
                    .startTime(event.getStartTime())
                    .endTime(event.getEndTime())
                    .location(location)
                    .dayEvents(DayEventsResponseDTO.DayEventsInfoDTO.from(event.getDayEvents()))
                    .build();
        }
    }
}

