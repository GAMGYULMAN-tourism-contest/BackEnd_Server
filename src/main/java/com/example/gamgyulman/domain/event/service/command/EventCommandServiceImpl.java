package com.example.gamgyulman.domain.event.service.command;

import com.example.gamgyulman.domain.dayEvents.entity.DayEvents;
import com.example.gamgyulman.domain.dayEvents.exception.DayEventsErrorCode;
import com.example.gamgyulman.domain.dayEvents.exception.DayEventsException;
import com.example.gamgyulman.domain.dayEvents.repository.DayEventsRepository;
import com.example.gamgyulman.domain.event.dto.EventRequestDTO;
import com.example.gamgyulman.domain.event.entity.Event;
import com.example.gamgyulman.domain.event.exception.EventErrorCode;
import com.example.gamgyulman.domain.event.exception.EventException;
import com.example.gamgyulman.domain.event.repository.EventRepository;
import com.example.gamgyulman.domain.location.dto.LocationRequestDTO;
import com.example.gamgyulman.domain.location.entity.Location;
import com.example.gamgyulman.domain.location.service.command.LocationCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EventCommandServiceImpl implements EventCommandService {

    private final DayEventsRepository dayEventsRepository;
    private final EventRepository eventRepository;
    private final LocationCommandService locationCommandService;

    @Override
    public Event createEvent(EventRequestDTO.CreateEventDTO dto) {
        DayEvents dayEvents = dayEventsRepository.findById(dto.getDayEventsId()).orElseThrow(() ->
                new DayEventsException(DayEventsErrorCode.NOT_FOUND));

        // 존재하는지 확인
        String start = dto.getStartTime();
        String end = dto.getEndTime();
        if (isExistEvent(null, dayEvents, start, end)) {
            throw new EventException(EventErrorCode.ALREADY_EXIST);
        }

        Location location = null;
        if (!dto.getLocationContentId().isEmpty() && !dto.getLocationContentTypeId().isEmpty()) {
            location = locationCommandService.createLocation(
                    LocationRequestDTO.LocationRequestInfoDTO.builder()
                            .contentId(dto.getLocationContentId())
                            .contentTypeId(dto.getLocationContentTypeId())
                            .build()
            );
        }
        return eventRepository.save(dto.toEntity(dayEvents, location));
    }

    @Override
    public Event updateEvent(EventRequestDTO.UpdateEventDTO dto) {
        DayEvents dayEvents = dayEventsRepository.findById(dto.getDayEventsId()).orElseThrow(() ->
                new DayEventsException(DayEventsErrorCode.NOT_FOUND));

        Event event = eventRepository.findById(dto.getEventId()).orElseThrow(() ->
                new EventException(EventErrorCode.NOT_FOUND));

        // 존재하는지 확인
        String start = dto.getStartTime();
        String end = dto.getEndTime();
        if (isExistEvent(event.getId(), dayEvents, start, end)) {
            throw new EventException(EventErrorCode.ALREADY_EXIST);
        }

        Location location = null;
//        if (dto.getLocationContentId() != null && dto.getLocationContentTypeId() != null) {
        if (!dto.getLocationContentId().isEmpty() && !dto.getLocationContentTypeId().isEmpty()) {
            LocationRequestDTO.LocationRequestInfoDTO locationDTO = LocationRequestDTO.LocationRequestInfoDTO.builder()
                    .contentId(dto.getLocationContentId())
                    .contentTypeId(dto.getLocationContentTypeId())
                    .build();
            if (event.getLocation() != null) {
                Location eventLocation = event.getLocation();
                eventLocation.update(locationDTO.getContentId(), locationDTO.getContentTypeId());
                location = eventLocation;
            }
            else {
                location = locationCommandService.createLocation(locationDTO);
            }
        }

        event.update(dto.getTitle(), dto.getDescription(), dto.getStartTime(), dto.getEndTime(), location, dayEvents);

        return event;
    }

    @Override
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    /**
     * 해당 DayEvents 내에 특정 범위 내의 event가 있는지 확인
     * @param start 범위의 시작 시간
     * @param end 범위의 끝 시간
     * @return 있으면 true, 없으면 false
     */
    private boolean isExistEvent(Long eventId, DayEvents dayEvents, String start, String end) {
        return eventId == null ?
                eventRepository.existsByDayEventsIsAndStartTimeIsBetweenOrEndTimeIsBetween(
                        dayEvents,
                        start,
                        end,
                        start,
                        end
                ) :
                eventRepository.existsByDayEventsIsAndStartTimeIsBetweenOrEndTimeIsBetweenAndIdIsNot(
                        eventId,
                        dayEvents,
                        start,
                        end,
                        start,
                        end
                );
    }
}
