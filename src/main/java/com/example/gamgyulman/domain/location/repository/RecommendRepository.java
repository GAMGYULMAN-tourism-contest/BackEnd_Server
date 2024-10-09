package com.example.gamgyulman.domain.location.repository;

import com.example.gamgyulman.domain.location.entity.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendRepository extends JpaRepository<Recommend, Long> {
}
