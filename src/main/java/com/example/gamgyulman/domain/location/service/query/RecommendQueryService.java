package com.example.gamgyulman.domain.location.service.query;

import com.example.gamgyulman.domain.location.dto.RecommendResponseDTO;

import java.util.List;

public interface RecommendQueryService {

    List<RecommendResponseDTO.RecommendInfoDTO> getRecommend();
}
