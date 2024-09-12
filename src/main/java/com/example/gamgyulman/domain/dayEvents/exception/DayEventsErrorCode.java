package com.example.gamgyulman.domain.dayEvents.exception;

import com.example.gamgyulman.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum DayEventsErrorCode implements BaseErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND, "DAYEVENTS404", "해당 일짜의 일정을 찾지 못했습니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private String message;

}
