package com.example.gamgyulman.domain.dayEvents.repository;

import com.example.gamgyulman.domain.dayEvents.entity.DayEvents;
import com.example.gamgyulman.domain.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DayEventsRepository extends JpaRepository<DayEvents, Long> {
    List<DayEvents> findAllByScheduleIsOrderByDay(Schedule schedule);
}
