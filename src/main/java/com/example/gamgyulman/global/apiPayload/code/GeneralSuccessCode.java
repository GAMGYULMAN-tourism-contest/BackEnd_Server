package com.example.gamgyulman.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum GeneralSuccessCode implements BaseSuccessCode {

    OK(HttpStatus.OK, "COMMON200", "성공했습니다."),
    CREATE(HttpStatus.CREATED, "COMMON201", "생성했습니다."),
    NO_CONTENT(HttpStatus.NO_CONTENT, "COMMON204", "삭제했습니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
