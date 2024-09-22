package com.example.gamgyulman.domain.member.service.query;

import com.example.gamgyulman.domain.member.entity.Invitation;
import com.example.gamgyulman.domain.member.entity.Member;
import org.springframework.data.domain.Slice;

public interface InvitationQueryService {

    Slice<Invitation> getInvitations(Member receiver, Long cursor, int offset);
    Long getCountOfInvitationNotRead(Member member);
}
