package com.example.gamgyulman.domain.dayEvents.service.query;

import com.example.gamgyulman.domain.dayEvents.entity.DayEvents;

import java.util.List;

public interface DayEventsQueryService {
    List<DayEvents> findAllDayEvents(Long scheduleId);
    DayEvents findDayEvents(Long id);

}
