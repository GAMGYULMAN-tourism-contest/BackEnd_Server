package com.example.gamgyulman.domain.member.exception;

import com.example.gamgyulman.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER404", "사용자를 찾지 못했습니다.")
    ;

    private HttpStatus status;
    private String code;
    private String message;
}
