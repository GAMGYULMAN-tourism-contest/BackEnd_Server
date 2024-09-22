package com.example.gamgyulman.domain.member.repository;

import com.example.gamgyulman.domain.member.entity.Invitation;
import com.example.gamgyulman.domain.member.entity.Member;
import com.example.gamgyulman.domain.member.entity.enums.InvitationStatus;
import com.example.gamgyulman.domain.schedule.entity.Schedule;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    Slice<Invitation> findAllByReceiverOrderByCreatedAtDesc(Member receiver, Pageable pageable);
    Slice<Invitation> findAllByReceiverAndCreatedAtLessThanOrderByCreatedAtDesc(Member receiver, LocalDateTime createdAt, Pageable pageable);
    void deleteAllBySchedule(Schedule schedule);
    Optional<Invitation> findByScheduleAndReceiverAndStatusNotIn(Schedule schedule, Member member, List<InvitationStatus> status);
    Long countAllByReceiverAndStatusIn(Member member, List<InvitationStatus> status);
}
