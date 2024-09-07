package com.example.gamgyulman.domain.member.service.query;

import com.example.gamgyulman.domain.member.entity.Member;

public interface MemberQueryService {
    Member getMember(String email);
}
