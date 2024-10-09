package com.example.gamgyulman.domain.location.service.query;

import com.example.gamgyulman.domain.location.dto.RecommendResponseDTO;
import com.example.gamgyulman.domain.location.entity.Recommend;
import com.example.gamgyulman.domain.location.repository.RecommendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendQueryServiceImpl implements RecommendQueryService{

    private final RecommendRepository recommendRepository;

    public List<RecommendResponseDTO.RecommendInfoDTO> getRecommend() {
        List<Recommend> recommends = recommendRepository.findAll();
        return recommends.stream().map(RecommendResponseDTO.RecommendInfoDTO::from).toList();
    }

}
