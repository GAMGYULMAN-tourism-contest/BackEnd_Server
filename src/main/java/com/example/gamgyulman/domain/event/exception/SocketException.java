package com.example.gamgyulman.domain.event.exception;

import com.example.gamgyulman.global.apiPayload.exception.GeneralException;

public class SocketException extends GeneralException {
    public SocketException(SocketErrorCode code) {
        super(code);
    }
}
