package com.example.gamgyulman.domain.location.exception;

import com.example.gamgyulman.global.apiPayload.exception.GeneralException;

public class LocationException extends GeneralException {
    public LocationException(LocationErrorCode code) {
        super(code);
    }
}
