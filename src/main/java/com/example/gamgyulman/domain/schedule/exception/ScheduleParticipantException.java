package com.example.gamgyulman.domain.schedule.exception;

import com.example.gamgyulman.global.apiPayload.exception.GeneralException;

public class ScheduleParticipantException extends GeneralException {
    public ScheduleParticipantException(ScheduleParticipantErrorCode code) {
        super(code);
    }
}
