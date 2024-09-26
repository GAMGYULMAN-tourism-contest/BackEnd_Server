package com.example.gamgyulman.domain.event.exception;

import com.example.gamgyulman.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SocketErrorCode implements BaseErrorCode {

    PARSING_ERROR(HttpStatus.BAD_REQUEST, "SOCKET400", "소켓의 body가 잘못되었습니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
