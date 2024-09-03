package com.example.gamgyulman.domain.member.exception;

import com.example.gamgyulman.global.apiPayload.code.BaseErrorCode;
import com.example.gamgyulman.global.apiPayload.exception.GeneralException;

public class JwtException extends GeneralException {

    public JwtException(JwtErrorCode code) {
        super(code);
    }

    @Override
    public BaseErrorCode getCode() {
        return super.getCode();
    }
}
