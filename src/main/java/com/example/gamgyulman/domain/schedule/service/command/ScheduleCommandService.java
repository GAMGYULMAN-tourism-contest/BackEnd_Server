package com.example.gamgyulman.domain.schedule.service.command;

import com.example.gamgyulman.domain.member.entity.Member;
import com.example.gamgyulman.domain.schedule.dto.ScheduleRequestDTO;
import com.example.gamgyulman.domain.schedule.entity.Schedule;

public interface ScheduleCommandService {

    Schedule createSchedule(Member member, ScheduleRequestDTO.CreateScheduleDTO dto);

    Schedule updateSchedule(Member member, Long scheduleId, ScheduleRequestDTO.UpdateScheduleDTO dto);

    void deleteSchedule(Member member, Long id);
}
