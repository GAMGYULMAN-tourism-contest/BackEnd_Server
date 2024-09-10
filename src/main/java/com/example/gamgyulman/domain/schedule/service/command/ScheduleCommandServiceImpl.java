package com.example.gamgyulman.domain.schedule.service.command;

import com.example.gamgyulman.domain.dayEvents.service.command.DayEventsCommandService;
import com.example.gamgyulman.domain.member.entity.Member;
import com.example.gamgyulman.domain.schedule.dto.ScheduleRequestDTO;
import com.example.gamgyulman.domain.schedule.entity.Schedule;
import com.example.gamgyulman.domain.schedule.entity.ScheduleParticipant;
import com.example.gamgyulman.domain.schedule.entity.enums.ScheduleParticipantRole;
import com.example.gamgyulman.domain.schedule.exception.ScheduleErrorCode;
import com.example.gamgyulman.domain.schedule.exception.ScheduleException;
import com.example.gamgyulman.domain.schedule.exception.ScheduleParticipantErrorCode;
import com.example.gamgyulman.domain.schedule.exception.ScheduleParticipantException;
import com.example.gamgyulman.domain.schedule.repository.ScheduleParticipantRepository;
import com.example.gamgyulman.domain.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleCommandServiceImpl implements ScheduleCommandService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleParticipantRepository scheduleParticipantRepository;
    private final DayEventsCommandService dayEventsCommandService;

    @Override
    public Schedule createSchedule(Member member, ScheduleRequestDTO.CreateScheduleDTO dto) {
        Schedule schedule = scheduleRepository.save(dto.toEntity());
        ScheduleParticipant participant = ScheduleParticipant.builder()
                .role(ScheduleParticipantRole.OWNER)
                .member(member)
                .schedule(schedule)
                .build();
        scheduleParticipantRepository.save(participant);

        dayEventsCommandService.createAllDayEvents(schedule);

        return schedule;
    }

    @Override
    public Schedule updateSchedule(Member member, Long scheduleId, ScheduleRequestDTO.UpdateScheduleDTO dto) {

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() ->
                new ScheduleException(ScheduleErrorCode.NOT_FOUND));
        ScheduleParticipant participant = scheduleParticipantRepository.findByScheduleIsAndMemberIs(schedule, member).orElseThrow(() ->
                new ScheduleParticipantException(ScheduleParticipantErrorCode.NOT_FOUND));

        ScheduleParticipantRole role = participant.getRole();
        boolean canModify = role.equals(ScheduleParticipantRole.OWNER) || role.equals(ScheduleParticipantRole.MANAGER);

        if (canModify) {
            schedule.update(
                    Optional.ofNullable(dto.getTitle()).orElse(schedule.getTitle()),
                    Optional.ofNullable(dto.getDescription()).orElse(schedule.getDescription()),
                    Optional.ofNullable(dto.getPeriod()).orElse(schedule.getPeriod()),
                    Optional.ofNullable(dto.getStartDate()).orElse(schedule.getStartDate())
            );
        }
        else {
            throw new ScheduleException(ScheduleErrorCode.FORBIDDEN_MODIFY);
        }

        dayEventsCommandService.updateDateOfDayEvents(schedule);

        return schedule;
    }

    @Override
    public void deleteSchedule(Member member, Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() ->
                new ScheduleException(ScheduleErrorCode.NOT_FOUND));
        ScheduleParticipant participant = scheduleParticipantRepository.findByScheduleIsAndMemberIs(schedule, member).orElseThrow(() ->
                new ScheduleParticipantException(ScheduleParticipantErrorCode.NOT_FOUND));

        ScheduleParticipantRole role = participant.getRole();
        if (role.equals(ScheduleParticipantRole.OWNER)) {
            // TODO: 삭제 로직 구현
        }
        else {
            throw new ScheduleException(ScheduleErrorCode.FORBIDDEN_DELETE);
        }
    }
}
