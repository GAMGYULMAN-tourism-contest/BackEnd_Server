package com.example.gamgyulman.domain.location.repository;

import com.example.gamgyulman.domain.location.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
