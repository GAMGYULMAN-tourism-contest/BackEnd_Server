package com.example.gamgyulman.domain.test.dto;

import com.example.gamgyulman.domain.test.validation.annotation.PositiveNum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class TestDTO {

    @Getter
    public static class InvalidDTO {
        @NotNull(message = "이름이 null일 수 없습니다.")
        private String name;
        @NotNull(message = "별명이 null일 수 없습니다.")
        private String nickname;
    }

    @Getter
    public static class PositiveDTO {
        @PositiveNum
        @Schema(defaultValue = "-1")
        private int value;
    }
}
