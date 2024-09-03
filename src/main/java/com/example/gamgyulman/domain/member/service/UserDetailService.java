package com.example.gamgyulman.domain.member.service;

import com.example.gamgyulman.domain.member.entity.CustomUserDetails;
import com.example.gamgyulman.domain.member.entity.Member;
import com.example.gamgyulman.domain.member.exception.MemberErrorCode;
import com.example.gamgyulman.domain.member.exception.MemberException;
import com.example.gamgyulman.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username).orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND));
        return new CustomUserDetails(member);
    }
}
