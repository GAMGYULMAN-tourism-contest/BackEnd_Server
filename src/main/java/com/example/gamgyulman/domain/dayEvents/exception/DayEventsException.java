package com.example.gamgyulman.domain.dayEvents.exception;

import com.example.gamgyulman.global.apiPayload.exception.GeneralException;

public class DayEventsException extends GeneralException {

    public DayEventsException(DayEventsErrorCode code) {
        super(code);
    }
}
