package com.example.gamgyulman.domain.schedule.dto;

import com.example.gamgyulman.domain.dayEvents.dto.DayEventsResponseDTO;
import com.example.gamgyulman.domain.schedule.entity.Schedule;
import com.example.gamgyulman.domain.schedule.entity.ScheduleParticipant;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ScheduleResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CreatedScheduleDTO {
        private Long id;
        private LocalDateTime createdAt;
        private List<Long> dayEvents;

        public static CreatedScheduleDTO from(Schedule schedule, List<Long> dayEvents) {
            return CreatedScheduleDTO.builder()
                    .id(schedule.getId())
                    .createdAt(schedule.getCreatedAt())
                    .dayEvents(dayEvents)
                    .build();
        }
    }

    @Builder
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class UpdatedScheduleDTO {
        private Long id;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private List<Long> dayEvents;

        public static UpdatedScheduleDTO from(Schedule schedule, List<Long> dayEvents) {
            return UpdatedScheduleDTO.builder()
                    .id(schedule.getId())
                    .createdAt(schedule.getCreatedAt())
                    .updatedAt(schedule.getUpdatedAt())
                    .dayEvents(dayEvents)
                    .build();
        }
    }

    @Builder
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class SchedulePreviewNoRoleDTO {
        private Long id;
        private String title;
        private String description;
        private int period;
        private LocalDate startDate;
        private LocalDate endDate;

        public static SchedulePreviewNoRoleDTO from(Schedule schedule) {
            return SchedulePreviewNoRoleDTO.builder()
                    .id(schedule.getId())
                    .title(schedule.getTitle())
                    .description(schedule.getDescription())
                    .period(schedule.getPeriod())
                    .startDate(schedule.getStartDate())
                    .endDate(schedule.getEndDate())
                    .build();
        }
    }

    @Builder
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class SchedulePreviewDTO {
        private Long id;
        private String title;
        private String description;
        private int period;
        private LocalDate startDate;
        private LocalDate endDate;
        private String role;

        public static SchedulePreviewDTO from(ScheduleParticipant scheduleParticipant) {
            Schedule schedule = scheduleParticipant.getSchedule();
            return SchedulePreviewDTO.builder()
                    .id(schedule.getId())
                    .title(schedule.getTitle())
                    .description(schedule.getDescription())
                    .period(schedule.getPeriod())
                    .startDate(schedule.getStartDate())
                    .endDate(schedule.getEndDate())
                    .role(scheduleParticipant.getRole().name())
                    .build();
        }
    }


    @Builder
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ScheduleInfoDTO {
        private Long id;
        private String title;
        private String description;
        private int period;
        private LocalDate startDate;
        private LocalDate endDate;
        private List<DayEventsResponseDTO.DayEventsInfoDTO> dayEvents;

        public static ScheduleInfoDTO from(Schedule schedule) {
            return ScheduleInfoDTO.builder()
                    .id(schedule.getId())
                    .title(schedule.getTitle())
                    .description(schedule.getDescription())
                    .period(schedule.getPeriod())
                    .startDate(schedule.getStartDate())
                    .endDate(schedule.getEndDate())
                    .dayEvents(schedule.getDayEventsList().stream().map(DayEventsResponseDTO.DayEventsInfoDTO::from).toList())
                    .build();
        }
    }

    @Builder
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class SchedulePreviewListDTO {
        private List<SchedulePreviewDTO> list;

        public static SchedulePreviewListDTO from(List<ScheduleParticipant> scheduleParticipants) {
            return SchedulePreviewListDTO.builder()
                    .list(scheduleParticipants.stream().map(SchedulePreviewDTO::from).toList())
                    .build();
        }
    }
}
