package com.example.gamgyulman.domain.schedule.dto;

import com.example.gamgyulman.domain.schedule.entity.Schedule;
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

        public static CreatedScheduleDTO from(Schedule schedule) {
            return CreatedScheduleDTO.builder()
                    .id(schedule.getId())
                    .createdAt(schedule.getCreatedAt())
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

        public static UpdatedScheduleDTO from(Schedule schedule) {
            return UpdatedScheduleDTO.builder()
                    .id(schedule.getId())
                    .createdAt(schedule.getCreatedAt())
                    .updatedAt(schedule.getUpdatedAt())
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

        public static SchedulePreviewDTO from(Schedule schedule) {
            return SchedulePreviewDTO.builder()
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
    public static class SchedulePreviewListDTO {
        private List<SchedulePreviewDTO> list;

        public static SchedulePreviewListDTO from(List<Schedule> scheduleList) {
            return SchedulePreviewListDTO.builder()
                    .list(scheduleList.stream().map(SchedulePreviewDTO::from).toList())
                    .build();
        }
    }
}
