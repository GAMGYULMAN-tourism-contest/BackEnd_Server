package com.example.gamgyulman.global.apiPayload;

import com.example.gamgyulman.global.apiPayload.code.BaseErrorCode;
import com.example.gamgyulman.global.apiPayload.code.BaseSuccessCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonPropertyOrder({"status", "code", "message", "isSuccess", "result"})
public class CustomResponse<T> {

    @JsonProperty("status")
    private final HttpStatus status;
    @JsonProperty("code")
    private final String code;
    @JsonProperty("message")
    private final String message;
    @JsonProperty("isSuccess")
    private final boolean isSuccess;
    @JsonProperty("result")
    private final T result;



    public static <T> CustomResponse<T> onSuccess(T result) {
        return new CustomResponse<>(HttpStatus.OK, String.valueOf(HttpStatus.OK.value()), HttpStatus.OK.getReasonPhrase(), true, result);
    }

    public static <T> CustomResponse<T> of(BaseSuccessCode code, T result) {
        return new CustomResponse<>(code.getStatus(), code.getCode(), code.getMessage(), true, result);
    }

    public static <T> CustomResponse<T> onFailure(BaseErrorCode code, T result) {
        return new CustomResponse<>(code.getStatus(), code.getCode(), code.getMessage(), false, result);
    }

    public static <T> CustomResponse<T> onFailure(BaseErrorCode code) {
        return new CustomResponse<>(code.getStatus(), code.getCode(), code.getMessage(), false, null);
    }
}
