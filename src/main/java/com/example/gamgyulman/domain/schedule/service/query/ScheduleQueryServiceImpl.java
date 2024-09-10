package com.example.gamgyulman.domain.schedule.service.query;

import com.example.gamgyulman.domain.member.entity.Member;
import com.example.gamgyulman.domain.schedule.entity.Schedule;
import com.example.gamgyulman.domain.schedule.entity.ScheduleParticipant;
import com.example.gamgyulman.domain.schedule.exception.ScheduleErrorCode;
import com.example.gamgyulman.domain.schedule.exception.ScheduleException;
import com.example.gamgyulman.domain.schedule.repository.ScheduleParticipantRepository;
import com.example.gamgyulman.domain.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleQueryServiceImpl implements ScheduleQueryService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleParticipantRepository scheduleParticipantRepository;

    @Override
    public List<Schedule> getSchedulesOfMember(Member member) {
        return scheduleParticipantRepository.findByMemberIsOrderByCreatedAtDesc(member)
                .stream()
                .map(ScheduleParticipant::getSchedule)
                .distinct()
                .toList();
    }

    @Override
    public Schedule getSchedule(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() ->
                new ScheduleException(ScheduleErrorCode.NOT_FOUND));
    }
}
