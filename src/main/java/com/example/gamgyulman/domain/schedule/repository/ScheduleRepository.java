package com.example.gamgyulman.domain.schedule.repository;

import com.example.gamgyulman.domain.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
