package com.example.gamgyulman.domain.member.service.query;

import com.example.gamgyulman.domain.member.entity.Member;
import com.example.gamgyulman.domain.member.exception.MemberErrorCode;
import com.example.gamgyulman.domain.member.exception.MemberException;
import com.example.gamgyulman.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryServiceImpl implements MemberQueryService{

    private final MemberRepository memberRepository;

    @Override
    public Member getMember(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND));
    }
}
