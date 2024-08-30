package com.example.gamgyulman.global.apiPayload.exception.handler;

import com.example.gamgyulman.global.apiPayload.CustomResponse;
import com.example.gamgyulman.global.apiPayload.code.BaseErrorCode;
import com.example.gamgyulman.global.apiPayload.code.GeneralErrorCode;
import com.example.gamgyulman.global.apiPayload.exception.GeneralException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    public CustomResponse<List<String>> constraintViolationException(ConstraintViolationException e) {

        log.error(Arrays.toString(e.getStackTrace()));
        List<String> message = e.getConstraintViolations().stream().map(violation -> violation.getMessage()).toList();

        return CustomResponse.onFailure(GeneralErrorCode.BAD_REQUEST_400, message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CustomResponse<Map<String, String>> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> error = new HashMap<>();
        e.getBindingResult().getFieldErrors().stream().forEach(field -> error.put(field.getField(), field.getDefaultMessage()));

        log.error(Arrays.toString(e.getStackTrace()));
        return CustomResponse.onFailure(GeneralErrorCode.BAD_REQUEST_400, error);
    }

    @ExceptionHandler(GeneralException.class)
    public CustomResponse<String> generalException(GeneralException e) {
        BaseErrorCode code = e.getCode();

        log.error(Arrays.toString(e.getStackTrace()));

        return CustomResponse.onFailure(code);
    }

    @ExceptionHandler(Exception.class)
    public CustomResponse<String> exception(Exception e) {

        log.error(Arrays.toString(e.getStackTrace()));
        CustomResponse<String> response = CustomResponse.onFailure(GeneralErrorCode.INTERNAL_SERVER_ERROR_500);

        return CustomResponse.onFailure(GeneralErrorCode.INTERNAL_SERVER_ERROR_500);
    }
}
