package com.example.gamgyulman.domain.member.exception;

import com.example.gamgyulman.global.apiPayload.exception.GeneralException;

public class AuthException extends GeneralException {

    public AuthException(AuthErrorCode code) {
        super(code);
    }
}
