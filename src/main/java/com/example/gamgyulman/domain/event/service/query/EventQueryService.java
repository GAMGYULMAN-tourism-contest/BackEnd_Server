package com.example.gamgyulman.domain.event.service.query;

import com.example.gamgyulman.domain.event.dto.EventResponseDTO;
import com.example.gamgyulman.domain.event.entity.Event;

public interface EventQueryService {
    EventResponseDTO.EventInfoDTO getEventInfo(Long eventId);
    EventResponseDTO.EventInfoDTO getEventInfo(Event event);
}
