package com.example.gamgyulman.domain.event.service.command;

import com.example.gamgyulman.domain.event.dto.EventRequestDTO;
import com.example.gamgyulman.domain.event.entity.Event;

public interface EventCommandService {
    Event createEvent(EventRequestDTO.CreateEventDTO dto);
    Event updateEvent(EventRequestDTO.UpdateEventDTO dto);
    void deleteEvent(Long id);
}
