package com.example.gamgyulman.domain.member.service.query;

import com.example.gamgyulman.domain.member.entity.Member;
import com.example.gamgyulman.domain.member.exception.MemberErrorCode;
import com.example.gamgyulman.domain.member.exception.MemberException;
import com.example.gamgyulman.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberQueryServiceImpl implements MemberQueryService{

    private final MemberRepository memberRepository;

    @Override
    public Member getMember(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND));
    }
}
