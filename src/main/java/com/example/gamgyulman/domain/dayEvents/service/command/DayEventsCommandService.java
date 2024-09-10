package com.example.gamgyulman.domain.dayEvents.service.command;

import com.example.gamgyulman.domain.dayEvents.entity.DayEvents;
import com.example.gamgyulman.domain.schedule.entity.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface DayEventsCommandService {

    List<DayEvents> createAllDayEvents(Schedule schedule);
    DayEvents createDayEvents(Schedule schedule, int day, LocalDate date);
    List<DayEvents> updateDateOfDayEvents(Schedule schedule);
    void deleteDayEvents(Long id);
}
