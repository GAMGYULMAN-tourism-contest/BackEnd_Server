package com.example.gamgyulman.domain.schedule.repository;

import com.example.gamgyulman.domain.member.entity.Member;
import com.example.gamgyulman.domain.schedule.entity.Schedule;
import com.example.gamgyulman.domain.schedule.entity.ScheduleParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ScheduleParticipantRepository extends JpaRepository<ScheduleParticipant, Long> {
    Optional<ScheduleParticipant> findByScheduleIsAndMemberIs(Schedule schedule, Member member);
    List<ScheduleParticipant> findByMemberIsOrderByCreatedAtDesc(Member member);
    void deleteAllBySchedule(Schedule schedule);

    @Query("SELECT part.member FROM ScheduleParticipant part WHERE part.schedule.id = :scheduleId")
    List<Member> findAllByScheduleIs(@Param("scheduleId") Long scheduleId);
}
