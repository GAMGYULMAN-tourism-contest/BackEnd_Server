package com.example.gamgyulman.domain.member.service.query;

import com.example.gamgyulman.domain.member.entity.Invitation;
import com.example.gamgyulman.domain.member.exception.InvitationErrorCode;
import com.example.gamgyulman.domain.member.exception.InvitationException;
import com.example.gamgyulman.domain.member.repository.InvitationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InvitationQueryServiceImpl implements InvitationQueryService{

    private final InvitationRepository invitationRepository;

    @Override
    public Slice<Invitation> getInvitations(Long cursor, int offset) {
        Pageable pageable = PageRequest.of(0, offset);
        Slice<Invitation> invitations;
        if (cursor == null) {
            invitations = invitationRepository.findAllByOrderByCreatedAtDesc(pageable);
        }
        else {
            Invitation invitation = invitationRepository.findById(cursor).orElseThrow(() ->
                    new InvitationException(InvitationErrorCode.NOT_FOUND));
            invitations = invitationRepository.findAllByCreatedAtGreaterThanOrderByCreatedAtDesc(invitation.getCreatedAt(), pageable);
        }
        return invitations;
    }
}
