package com.example.gamgyulman.domain.schedule.service.query;

import com.example.gamgyulman.domain.member.entity.Member;
import com.example.gamgyulman.domain.schedule.dto.ScheduleResponseDTO;
import com.example.gamgyulman.domain.schedule.entity.Schedule;
import com.example.gamgyulman.domain.schedule.entity.ScheduleParticipant;

import java.util.List;

public interface ScheduleQueryService {

    List<ScheduleParticipant> getSchedulesOfMember(Member member);
    Schedule getSchedule(Long id);
}
