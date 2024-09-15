package com.example.gamgyulman.domain.openApi.controller;

import com.example.gamgyulman.domain.openApi.dto.TravelInfoResponseDTO;
import com.example.gamgyulman.domain.openApi.service.OpenApiService;
import com.example.gamgyulman.global.apiPayload.CustomResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/travels")
@Tag(name = "여행 정보 API")
@RequiredArgsConstructor
public class TravelInfoController {

    private final OpenApiService openApiService;

    // 초기 화면 가져오기
    // TODO: 어떤 것으로 가져올지 한 번 상의해보기
    @GetMapping
    public CustomResponse<TravelInfoResponseDTO.DefaultTravelInfoListDTO> getDefaultTravel(@RequestParam int page,
                                                                  @RequestParam int size) {
        TravelInfoResponseDTO.DefaultTravelInfoListDTO response = openApiService.getDefaultTravel(page, size);
        return CustomResponse.onSuccess(response);
    }

    // 키워드 검색
    // 상세 검색

}
