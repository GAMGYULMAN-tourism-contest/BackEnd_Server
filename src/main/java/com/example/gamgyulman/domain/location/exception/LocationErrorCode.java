package com.example.gamgyulman.domain.location.exception;

import com.example.gamgyulman.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum LocationErrorCode implements BaseErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND, "LOCATION404", "저장되지 않은 위치입니다.")
    ;
    private final HttpStatus status;
    private final String code;
    private final String message;
}
