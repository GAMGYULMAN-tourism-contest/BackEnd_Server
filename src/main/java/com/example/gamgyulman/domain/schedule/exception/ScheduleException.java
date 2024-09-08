package com.example.gamgyulman.domain.schedule.exception;

import com.example.gamgyulman.global.apiPayload.exception.GeneralException;

public class ScheduleException extends GeneralException {
    public ScheduleException(ScheduleErrorCode code) {
        super(code);
    }
}
