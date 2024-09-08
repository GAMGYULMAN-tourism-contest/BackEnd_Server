package com.example.gamgyulman.domain.schedule.dto;

import com.example.gamgyulman.domain.schedule.entity.Schedule;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.time.LocalDate;

public class ScheduleRequestDTO {

    @Getter
    public static class CreateScheduleDTO {
        @NotBlank
        @Schema(defaultValue = "제주 여행 스케줄")
        private String title;
        @NotBlank
        @Schema(defaultValue = "이번 가을 제주 여행 스케줄")
        private String description;
        @Positive
        @Schema(defaultValue = "5")
        private Integer period;
        @NotNull
        @Schema(defaultValue = "2024-11-04")
        private LocalDate startDate;

        public Schedule toEntity() {
            return Schedule.builder()
                    .title(this.title)
                    .description(this.description)
                    .period(this.period)
                    .startDate(this.startDate)
                    .endDate(this.startDate.plusDays(period))
                    .build();
        }
    }

    @Getter
    public static class UpdateScheduleDTO {
        @Schema(defaultValue = "제주 여행 스케줄 (수정)")
        private String title;
        @Schema(defaultValue = "이번 겨울 제주 여행 스케줄")
        private String description;
        @Positive
        @Schema(defaultValue = "5")
        private Integer period;
        @Schema(defaultValue = "2024-12-16")
        private LocalDate startDate;
    }
}
