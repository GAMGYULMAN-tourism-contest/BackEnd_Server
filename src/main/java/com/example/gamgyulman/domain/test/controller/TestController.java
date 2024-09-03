package com.example.gamgyulman.domain.test.controller;

import com.example.gamgyulman.domain.test.dto.TestDTO;
import com.example.gamgyulman.domain.test.validation.annotation.PositiveNum;
import com.example.gamgyulman.global.apiPayload.CustomResponse;
import com.example.gamgyulman.global.apiPayload.code.GeneralErrorCode;
import com.example.gamgyulman.global.apiPayload.code.GeneralSuccessCode;
import com.example.gamgyulman.global.apiPayload.exception.GeneralException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@Tag(name = "테스트 용도")
@Slf4j
@Validated
public class TestController {

    @GetMapping("/success")
    @Operation(summary = "성공 테스트용 API", description = "테스트용 API (Hello World 문자열 반환)")
    public CustomResponse<String> successTest() {
        return CustomResponse.of(GeneralSuccessCode.OK, "Hello Wolrd");
    }

    @GetMapping("/fail")
    @Operation(summary = "실패 테스트용 API", description = "테스트용 API (Error 문자열 반환)")
    public CustomResponse<String> failTest() {
        return CustomResponse.onFailure(GeneralErrorCode.BAD_REQUEST_400, "Error");
    }

    @GetMapping("/exception")
    @Operation(summary = "예외 처리 테스트용 API", description = "Exception 테스트용 API (Not Found 반환)")
    public CustomResponse<String> exceptionTest() {
        throw new GeneralException(GeneralErrorCode.NOT_FOUND_404);
    }

    @PostMapping("/methods/arguments")
    @Operation(summary = "부적절한 Request Body", description = "Request Body가 잘못되어 Valid에 걸리는 경우")
    public CustomResponse<String> invalidTest(@Valid @RequestBody TestDTO.InvalidDTO dto) {
        return CustomResponse.onSuccess("Request Body가 정상적입니다.");
    }

    @PostMapping("/constraint")
    @Operation(summary = "Annotation으로 부적절한 Param, field 검사", description = "Request Body의 field 값이 조건에 안 맞거나 Request Parameter가 조건에 안 맞는 경우")
    public CustomResponse<String> constraintTest(@PositiveNum int positive) {
        return CustomResponse.onSuccess("Request Param이 정상적입니다.");
    }
}
