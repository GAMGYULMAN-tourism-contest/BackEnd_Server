package com.example.gamgyulman.domain.openApi.service;

import com.example.gamgyulman.domain.openApi.dto.TravelInfoResponseDTO;

public interface OpenApiService {
    TravelInfoResponseDTO.DefaultTravelInfoListDTO getDefaultTravel(int page, int size);
    TravelInfoResponseDTO.DefaultTravelInfoListDTO searchKeyword(String keyword, int page, int size);
    TravelInfoResponseDTO.DefaultTravelInfoListDTO getTravels(String keyword, int page, int size);
}
