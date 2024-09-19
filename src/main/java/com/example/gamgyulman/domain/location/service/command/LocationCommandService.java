package com.example.gamgyulman.domain.location.service.command;

import com.example.gamgyulman.domain.location.dto.LocationRequestDTO;
import com.example.gamgyulman.domain.location.entity.Location;

public interface LocationCommandService {

    Location createLocation(LocationRequestDTO.LocationRequestInfoDTO dto);
    Location updateLocation(Long locationId, LocationRequestDTO.LocationRequestInfoDTO dto);
    void deleteLocation(Long locationId);
}
