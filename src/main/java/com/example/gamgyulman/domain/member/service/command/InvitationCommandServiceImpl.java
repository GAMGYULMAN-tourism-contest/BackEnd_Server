package com.example.gamgyulman.domain.member.service.command;

import com.example.gamgyulman.domain.member.dto.InvitationRequestDTO;
import com.example.gamgyulman.domain.member.entity.Invitation;
import com.example.gamgyulman.domain.member.entity.Member;
import com.example.gamgyulman.domain.member.entity.enums.InvitationStatus;
import com.example.gamgyulman.domain.member.exception.InvitationErrorCode;
import com.example.gamgyulman.domain.member.exception.InvitationException;
import com.example.gamgyulman.domain.member.exception.MemberErrorCode;
import com.example.gamgyulman.domain.member.exception.MemberException;
import com.example.gamgyulman.domain.member.repository.InvitationRepository;
import com.example.gamgyulman.domain.member.repository.MemberRepository;
import com.example.gamgyulman.domain.schedule.entity.Schedule;
import com.example.gamgyulman.domain.schedule.entity.ScheduleParticipant;
import com.example.gamgyulman.domain.schedule.entity.enums.ScheduleParticipantRole;
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
@Transactional
public class InvitationCommandServiceImpl implements InvitationCommandService{

    private final InvitationRepository invitationRepository;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleParticipantRepository scheduleParticipantRepository;
    private final MemberRepository memberRepository;

    @Override
    public Invitation createInvitation(Member member, InvitationRequestDTO.CreateInvitationDTO dto) {

        Schedule schedule = scheduleRepository.findById(dto.getScheduleId()).orElseThrow(() ->
                new ScheduleException(ScheduleErrorCode.NOT_FOUND));

        Member receiver = memberRepository.findByEmail(dto.getReceiverEmail()).orElseThrow(() ->
                new MemberException(MemberErrorCode.NOT_FOUND));

        // 본인인지 확인
        if (member.getId().equals(receiver.getId())) {
            throw new InvitationException(InvitationErrorCode.CANNOT_INVITE_YOURSELF);
        }

        // 참여자인지 확인
        if (isParticipant(dto.getScheduleId(), dto.getReceiverEmail())) {
            throw new InvitationException(InvitationErrorCode.ALREADY_PARTICIPANT);
        }

        // 초대 중인지 확인
        if (invitationRepository.findByScheduleAndReceiverAndStatusNotIn(schedule, receiver, List.of(InvitationStatus.ACCEPT, InvitationStatus.DENIED)).isPresent()) {
            throw new InvitationException(InvitationErrorCode.ALREADY_EXIST);
        }

        return invitationRepository.save(
                Invitation.builder()
                        .sender(member)
                        .receiver(receiver)
                        .schedule(schedule)
                        .status(InvitationStatus.NOT_READ)
                        .build()
        );
    }

    @Override
    public Invitation updateInvitation(InvitationRequestDTO.UpdateInvitationStatusDTO dto) {
        String status = dto.getStatus();

        Invitation invitation = invitationRepository.findById(dto.getInvitationId()).orElseThrow(() ->
                new InvitationException(InvitationErrorCode.NOT_FOUND));

        if (!isReadOrNotRead(invitation.getStatus().name())) {
            throw new InvitationException(InvitationErrorCode.ALREADY_PROCESSED);
        }

        if (isReadOrNotRead(dto.getStatus())) {
            throw new InvitationException(InvitationErrorCode.UNSUPPORTED_STATUS);
        }

        invitation.setStatus(InvitationStatus.valueOf(status));

        if (dto.getStatus().equalsIgnoreCase(InvitationStatus.ACCEPT.name())) {
            scheduleParticipantRepository.save(
                    ScheduleParticipant.builder()
                            .role(ScheduleParticipantRole.GUEST)
                            .member(invitation.getReceiver())
                            .schedule(invitation.getSchedule())
                            .build()
            );
        }

        return invitation;
    }

    @Override
    public void deleteInvitation(Long invitationId) {
        invitationRepository.deleteById(invitationId);
    }

    @Override
    public void updateStatus(List<Invitation> invitations, InvitationStatus status) {
        invitations.forEach(invitation -> invitation.setStatus(status));
    }

    private boolean isParticipant(Long scheduleId, String email) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() ->
                new ScheduleException(ScheduleErrorCode.NOT_FOUND));

        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
                new MemberException(MemberErrorCode.NOT_FOUND));

        return scheduleParticipantRepository.findByScheduleIsAndMemberIs(schedule, member).isPresent();
    }

    private boolean isReadOrNotRead(String status) {
        return status.equalsIgnoreCase(InvitationStatus.READ.name()) || status.equalsIgnoreCase(InvitationStatus.NOT_READ.name());
    }
}
