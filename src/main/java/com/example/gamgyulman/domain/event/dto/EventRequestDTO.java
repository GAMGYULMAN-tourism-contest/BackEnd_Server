package com.example.gamgyulman.domain.event.dto;

import com.example.gamgyulman.domain.dayEvents.entity.DayEvents;
import com.example.gamgyulman.domain.event.entity.Event;
import com.example.gamgyulman.domain.location.entity.Location;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class EventRequestDTO {

    @Getter
    public static class CreateEventDTO {
        @NotNull
        private Long dayEventsId;
        private String title;
        private String description;
        @NotNull
        private String startTime;
        @NotNull
        private String endTime;
        private String locationContentId;
        private String locationContentTypeId;

        public Event toEntity(DayEvents dayEvents, Location location) {
            return Event.builder()
                    .title(this.title)
                    .description(this.description)
                    .startTime(startTime)
                    .endTime(endTime)
                    .dayEvents(dayEvents)
                    .location(location)
                    .build();
        }
    }

    @Getter
    public static class UpdateEventDTO {
        @NotNull
        private Long eventId;
        @NotNull
        private Long dayEventsId;
        private String title;
        private String description;
        @NotNull
        private String startTime;
        @NotNull
        private String endTime;
        private String locationContentId;
        private String locationContentTypeId;

    }
}
