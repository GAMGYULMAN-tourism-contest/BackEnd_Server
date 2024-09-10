package com.example.gamgyulman.domain.dayEvents.service.command;

import com.example.gamgyulman.domain.dayEvents.entity.DayEvents;
import com.example.gamgyulman.domain.dayEvents.repository.DayEventsRepository;
import com.example.gamgyulman.domain.schedule.entity.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DayEventsCommandServiceImpl implements DayEventsCommandService{

    private final DayEventsRepository dayEventsRepository;

    @Override
    public List<DayEvents> createAllDayEvents(Schedule schedule) {
        int period = schedule.getPeriod();
        LocalDate start = schedule.getStartDate();

        List<DayEvents> result = new ArrayList<>();
        for (int i = 0; i < period; i++) {
            result.add(createDayEvents(schedule, i + 1, start.plusDays(i)));
        }

        return result;
    }

    @Override
    public List<DayEvents> updateDateOfDayEvents(Schedule schedule) {
        List<DayEvents> dayEventsList = dayEventsRepository.findAllByScheduleIsOrderByDay(schedule);
        dayEventsList.sort(Comparator.comparing(DayEvents::getDay));

        ArrayDeque<DayEvents> queue = new ArrayDeque<>();
        for (DayEvents events : dayEventsList) {
            queue.addLast(events);
        }
        List<DayEvents> result = new ArrayList<>();

        LocalDate start = schedule.getStartDate();
        for (int i = 0; i < schedule.getPeriod(); i++) {
            if (queue.isEmpty()) {
                result.add(createDayEvents(schedule, i + 1, start.plusDays(i)));
            }
            else {
                DayEvents dayEvents = queue.pollFirst();
                dayEvents.updateDate(start.plusDays(i));
                result.add(dayEvents);
            }
        }

        while (!queue.isEmpty()) {
            deleteDayEvents(queue.pollFirst().getId());
        }

        return result;
    }

    @Override
    public DayEvents createDayEvents(Schedule schedule, int day, LocalDate date) {
        return dayEventsRepository.save(
                DayEvents.builder()
                        .day(day)
                        .date(date)
                        .schedule(schedule)
                        .build()
        );
    }

    @Override
    public void deleteDayEvents(Long id) {
        dayEventsRepository.deleteById(id);
    }
}
