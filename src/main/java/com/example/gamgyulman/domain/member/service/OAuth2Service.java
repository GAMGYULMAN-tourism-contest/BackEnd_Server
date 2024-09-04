package com.example.gamgyulman.domain.member.service;

import com.example.gamgyulman.domain.member.dto.MemberResponseDTO;

public interface OAuth2Service {

    MemberResponseDTO.OAuthResponseDTO login(String provider, String code);
}
