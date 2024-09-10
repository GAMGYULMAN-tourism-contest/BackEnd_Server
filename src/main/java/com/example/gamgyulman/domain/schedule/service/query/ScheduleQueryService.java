package com.example.gamgyulman.domain.schedule.service.query;

import com.example.gamgyulman.domain.member.entity.Member;
import com.example.gamgyulman.domain.schedule.entity.Schedule;

import java.util.List;

public interface ScheduleQueryService {

    List<Schedule> getSchedulesOfMember(Member member);
    Schedule getSchedule(Long id);
}
