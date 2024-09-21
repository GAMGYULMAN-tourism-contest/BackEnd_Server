package com.example.gamgyulman.domain.member.service.query;

import com.example.gamgyulman.domain.member.entity.Invitation;
import org.springframework.data.domain.Slice;

public interface InvitationQueryService {

    Slice<Invitation> getInvitations(Long cursor, int offset);
}
