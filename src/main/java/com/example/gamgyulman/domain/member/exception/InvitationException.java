package com.example.gamgyulman.domain.member.exception;

import com.example.gamgyulman.global.apiPayload.exception.GeneralException;

public class InvitationException extends GeneralException {
    public InvitationException(InvitationErrorCode code) {
        super(code);
    }
}
