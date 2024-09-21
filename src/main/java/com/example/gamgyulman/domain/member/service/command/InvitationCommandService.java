package com.example.gamgyulman.domain.member.service.command;

import com.example.gamgyulman.domain.member.dto.InvitationRequestDTO;
import com.example.gamgyulman.domain.member.entity.Invitation;
import com.example.gamgyulman.domain.member.entity.Member;
import com.example.gamgyulman.domain.member.entity.enums.InvitationStatus;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface InvitationCommandService {
    Invitation createInvitation(Member member, InvitationRequestDTO.CreateInvitationDTO dto);
    Invitation updateInvitation(InvitationRequestDTO.UpdateInvitationStatusDTO dto);
    void deleteInvitation(Long invitationId);
    void updateStatus(List<Invitation> invitations, InvitationStatus status);
}
