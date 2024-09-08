package com.example.gamgyulman.domain.schedule.exception;

import com.example.gamgyulman.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ScheduleParticipantErrorCode implements BaseErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND, "SCHEDULEPARTICIPANT404", "사용자가 스케줄에 참여하고 있지 않습니다.")

    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
