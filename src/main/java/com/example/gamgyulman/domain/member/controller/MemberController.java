package com.example.gamgyulman.domain.member.controller;

import com.example.gamgyulman.domain.member.annotation.AuthenticatedMember;
import com.example.gamgyulman.domain.member.dto.MemberResponseDTO;
import com.example.gamgyulman.domain.member.entity.Member;
import com.example.gamgyulman.global.apiPayload.CustomResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "유저 API")
@RequestMapping("/members")
public class MemberController {

    @GetMapping
    public CustomResponse<MemberResponseDTO.MemberInfoDTO> currentMember(@AuthenticatedMember Member member) {
        return CustomResponse.onSuccess(MemberResponseDTO.MemberInfoDTO.from(member));
    }
}
