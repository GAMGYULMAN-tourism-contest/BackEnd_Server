package com.example.gamgyulman.domain.location.controller;

import com.example.gamgyulman.domain.location.dto.RecommendResponseDTO;
import com.example.gamgyulman.domain.location.service.query.RecommendQueryService;
import com.example.gamgyulman.global.apiPayload.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recommendations")
@RequiredArgsConstructor
@Tag(name = "추천 API")
public class RecommendController {

    private final RecommendQueryService recommendQueryService;

    //TODO: 프론트와 이야기해서 수정 필요
    @GetMapping
    @Operation(summary = "추천 조회 API", description = "추천 목록 가져오기")
    public CustomResponse<List<RecommendResponseDTO.RecommendInfoDTO>> getRecommend() {
        return CustomResponse.onSuccess(recommendQueryService.getRecommend());
    }
}
