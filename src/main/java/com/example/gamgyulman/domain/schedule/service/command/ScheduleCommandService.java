package com.example.gamgyulman.domain.schedule.service.command;

import com.example.gamgyulman.domain.member.entity.Member;
import com.example.gamgyulman.domain.schedule.dto.ScheduleRequestDTO;
import com.example.gamgyulman.domain.schedule.dto.ScheduleResponseDTO;
import com.example.gamgyulman.domain.schedule.entity.Schedule;

public interface ScheduleCommandService {

    ScheduleResponseDTO.CreatedScheduleDTO createSchedule(Member member, ScheduleRequestDTO.CreateScheduleDTO dto);

    ScheduleResponseDTO.UpdatedScheduleDTO updateSchedule(Member member, Long scheduleId, ScheduleRequestDTO.UpdateScheduleDTO dto);

    void deleteSchedule(Member member, Long id);
}
