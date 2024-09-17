package com.example.gamgyulman.domain.openApi.service;

import com.example.gamgyulman.domain.openApi.dto.TravelInfoResponseDTO;

public interface OpenApiService {
    TravelInfoResponseDTO.DefaultTravelInfoListDTO getTravels(String keyword, int page, int size);
    TravelInfoResponseDTO.TravelDetailInfoDTO getDetailOfTravel(String contentId, int page, int size);
}
