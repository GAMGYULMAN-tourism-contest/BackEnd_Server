package com.example.gamgyulman.domain.event.service.query;

import com.example.gamgyulman.domain.event.dto.EventResponseDTO;
import com.example.gamgyulman.domain.event.entity.Event;
import com.example.gamgyulman.domain.event.exception.EventErrorCode;
import com.example.gamgyulman.domain.event.exception.EventException;
import com.example.gamgyulman.domain.event.repository.EventRepository;
import com.example.gamgyulman.domain.openApi.dto.TravelInfoResponseDTO;
import com.example.gamgyulman.domain.openApi.service.OpenApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventQueryServiceImpl implements EventQueryService{

    private final EventRepository eventRepository;
    private final OpenApiService openApiService;

    @Override
    public EventResponseDTO.EventInfoDTO getEventInfo(Long eventId) {
        return getEventInfo(eventRepository.findById(eventId).orElseThrow(() ->
                new EventException(EventErrorCode.NOT_FOUND)));
    }

    @Override
    public EventResponseDTO.EventInfoDTO getEventInfo(Event event) {

        TravelInfoResponseDTO.TravelDetailInfoDTO travelDetailInfoDTO = null;
        if (event.getLocation() != null) {
            travelDetailInfoDTO = openApiService.getDetailOfTravel(event.getLocation().getContentId(), 1, 1);
        }
        return EventResponseDTO.EventInfoDTO.from(event, travelDetailInfoDTO);
    }
}
