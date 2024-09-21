package com.example.gamgyulman.domain.member.repository;

import com.example.gamgyulman.domain.member.entity.Invitation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    Slice<Invitation> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Slice<Invitation> findAllByCreatedAtGreaterThanOrderByCreatedAtDesc(LocalDateTime createdAt, Pageable pageable);
}
