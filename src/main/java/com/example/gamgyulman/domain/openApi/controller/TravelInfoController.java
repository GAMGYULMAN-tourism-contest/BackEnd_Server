package com.example.gamgyulman.domain.openApi.controller;

import com.example.gamgyulman.domain.openApi.dto.TravelInfoResponseDTO;
import com.example.gamgyulman.domain.openApi.service.OpenApiService;
import com.example.gamgyulman.global.apiPayload.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/travels")
@Tag(name = "여행 정보 API")
@RequiredArgsConstructor
public class TravelInfoController {

    private final OpenApiService openApiService;

    // 초기 화면 가져오기 & 키워드 검색
    @GetMapping
    @Operation(summary = "키워드 검색 & Default 검색", description = "키워드를 검색하거나 키워드가 없을 시 Default 검색 결과를 반환")
    @Parameters({
            @Parameter(name = "keyword", description = "검색할 키워드, 없을 시 default 결과값 반환"),
            @Parameter(name = "page", description = "페이지 수, 첫 페이지 = 1"),
            @Parameter(name = "size", description = "한 페이지의 검색 결과 개수")
    })
    public CustomResponse<TravelInfoResponseDTO.DefaultTravelInfoListDTO> searchTravelWithKeyword(@RequestParam(required = false) String keyword,
                                                                                                  @RequestParam(defaultValue = "1") int page,
                                                                                                  @RequestParam(defaultValue = "10") int size) {
        TravelInfoResponseDTO.DefaultTravelInfoListDTO response = openApiService.getTravels(keyword, page, size);
        return CustomResponse.onSuccess(response);
    }

    // 정보 상세 검색
    @GetMapping("/{contentId}")
    @Operation(summary = "여행 정보 상세 검색", description = "contentId를 이용하여 여행 장소의 상세 정보 가져오기")
    @Parameter(name = "contentId", description = "ID(PK) 값으로 사용하는 contentId")
    public CustomResponse<TravelInfoResponseDTO.TravelDetailInfoDTO> getDetailOfTravel(@PathVariable("contentId") String contentId,
                                                                                       @RequestParam(defaultValue = "1") int page,
                                                                                       @RequestParam(defaultValue = "10") int size) {
        TravelInfoResponseDTO.TravelDetailInfoDTO response = openApiService.getDetailOfTravel(contentId, page, size);
        return CustomResponse.onSuccess(response);
    }

}
