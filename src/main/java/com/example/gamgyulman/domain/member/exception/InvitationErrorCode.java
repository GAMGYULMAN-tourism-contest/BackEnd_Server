package com.example.gamgyulman.domain.member.exception;

import com.example.gamgyulman.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum InvitationErrorCode implements BaseErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND, "INVITATION404", "초대를 찾지 못했습니다."),
    UNSUPPORTED_STATUS(HttpStatus.BAD_REQUEST, "INVITATION400", "지원하지 않는 초대 상태입니다."),
    ALREADY_EXIST(HttpStatus.BAD_REQUEST,"INVITATION400", "이미 초대를 보냈습니다."),
    ALREADY_PARTICIPANT(HttpStatus.BAD_REQUEST,"INVITATION400", "이미 참여자입니다."),
    ALREADY_PROCESSED(HttpStatus.BAD_REQUEST, "INVITATION400", "이미 처리된 초대입니다."),
    CANNOT_INVITE_YOURSELF(HttpStatus.BAD_REQUEST, "INVITATION400", "본인에게 초대를 보낼 수 없습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
