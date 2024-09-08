package com.example.gamgyulman.domain.schedule.repository;

import com.example.gamgyulman.domain.member.entity.Member;
import com.example.gamgyulman.domain.schedule.entity.Schedule;
import com.example.gamgyulman.domain.schedule.entity.ScheduleParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScheduleParticipantRepository extends JpaRepository<ScheduleParticipant, Long> {
    Optional<ScheduleParticipant> findByScheduleIsAndMemberIs(Schedule schedule, Member member);
}
