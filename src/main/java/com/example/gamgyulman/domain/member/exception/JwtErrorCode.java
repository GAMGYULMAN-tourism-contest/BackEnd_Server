package com.example.gamgyulman.domain.member.exception;

import com.example.gamgyulman.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum JwtErrorCode implements BaseErrorCode {

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN401", "토큰이 유효하지 않습니다."),
    NOT_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "TOKEN400", "Access Token이 아닙니다."),
    NOT_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "TOKEN400", "Refresh Token이 아닙니다."),
    ;
    private HttpStatus status;
    private String code;
    private String message;
}
