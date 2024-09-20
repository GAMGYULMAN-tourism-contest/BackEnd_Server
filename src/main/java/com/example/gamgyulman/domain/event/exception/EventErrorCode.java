package com.example.gamgyulman.domain.event.exception;

import com.example.gamgyulman.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum EventErrorCode implements BaseErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND, "EVENT404", "일정을 찾지 못했습니다."),
    ALREADY_EXIST(HttpStatus.BAD_REQUEST, "EVENT401", "해당 시간에 이미 일정이 존재합니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
