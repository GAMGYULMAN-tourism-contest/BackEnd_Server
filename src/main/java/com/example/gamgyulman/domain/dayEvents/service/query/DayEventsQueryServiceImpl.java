package com.example.gamgyulman.domain.dayEvents.service.query;

import com.example.gamgyulman.domain.dayEvents.entity.DayEvents;
import com.example.gamgyulman.domain.dayEvents.exception.DayEventsErrorCode;
import com.example.gamgyulman.domain.dayEvents.exception.DayEventsException;
import com.example.gamgyulman.domain.dayEvents.repository.DayEventsRepository;
import com.example.gamgyulman.domain.schedule.entity.Schedule;
import com.example.gamgyulman.domain.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DayEventsQueryServiceImpl implements DayEventsQueryService {

    private final DayEventsRepository dayEventsRepository;
    private final ScheduleRepository scheduleRepository;

    @Override
    public List<DayEvents> findAllDayEvents(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() ->
                new DayEventsException(DayEventsErrorCode.NOT_FOUND));
        return dayEventsRepository.findAllByScheduleIsOrderByDay(schedule);
    }

    @Override
    public DayEvents findDayEvents(Long id) {
        return dayEventsRepository.findById(id).orElseThrow(() ->
                new DayEventsException(DayEventsErrorCode.NOT_FOUND));
    }
}
