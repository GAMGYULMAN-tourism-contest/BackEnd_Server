package com.example.gamgyulman.domain.location.service.command;

import com.example.gamgyulman.domain.location.dto.LocationRequestDTO;
import com.example.gamgyulman.domain.location.entity.Location;
import com.example.gamgyulman.domain.location.exception.LocationErrorCode;
import com.example.gamgyulman.domain.location.exception.LocationException;
import com.example.gamgyulman.domain.location.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LocationCommandServiceImpl implements LocationCommandService {

    private final LocationRepository locationRepository;

    @Override
    public Location createLocation(LocationRequestDTO.LocationRequestInfoDTO dto) {
        return locationRepository.save(dto.toEntity());
    }

    @Override
    public Location updateLocation(Long locationId, LocationRequestDTO.LocationRequestInfoDTO dto) {
        Location location = locationRepository.findById(locationId).orElseThrow(() ->
                new LocationException(LocationErrorCode.NOT_FOUND));

        location.update(dto.getContentId(), dto.getContentTypeId());
        return location;
    }

    @Override
    public void deleteLocation(Long locationId) {
        locationRepository.deleteById(locationId);
    }
}
