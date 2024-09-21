package com.example.gamgyulman.domain.member.entity;

import com.example.gamgyulman.domain.member.entity.enums.InvitationStatus;
import com.example.gamgyulman.domain.schedule.entity.Schedule;
import com.example.gamgyulman.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Invitation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invitation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Member sender;  // 보낸 사람

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Member receiver;    // 받는 사람

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private InvitationStatus status;

    public void setStatus(InvitationStatus status) {
        this.status = status;
    }
}
