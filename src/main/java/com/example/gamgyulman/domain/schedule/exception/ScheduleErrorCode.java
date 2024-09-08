package com.example.gamgyulman.domain.schedule.exception;

import com.example.gamgyulman.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ScheduleErrorCode implements BaseErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND, "SCHEDULE404", "스케줄을 찾지 못했습니다."),
    FORBIDDEN_MODIFY(HttpStatus.FORBIDDEN, "SCHEDULE403", "스케줄을 변경할 권한이 없습니다."),
    FORBIDDEN_DELETE(HttpStatus.FORBIDDEN, "SCHEDULE403", "스케줄을 삭제할 권한이 없습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
