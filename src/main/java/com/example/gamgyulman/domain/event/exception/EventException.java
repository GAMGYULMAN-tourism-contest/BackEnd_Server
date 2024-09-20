package com.example.gamgyulman.domain.event.exception;

import com.example.gamgyulman.global.apiPayload.exception.GeneralException;

public class EventException extends GeneralException {
    public EventException(EventErrorCode code) {
        super(code);
    }
}
