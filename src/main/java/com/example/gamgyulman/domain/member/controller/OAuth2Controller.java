package com.example.gamgyulman.domain.member.controller;

import com.example.gamgyulman.domain.member.dto.MemberResponseDTO;
import com.example.gamgyulman.domain.member.dto.OAuth2DTO;
import com.example.gamgyulman.domain.member.service.OAuth2Service;
import com.example.gamgyulman.global.apiPayload.CustomResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/login/oauth2")
@RequiredArgsConstructor
@Tag(name = "OAuth2 login")
public class OAuth2Controller {

    private final OAuth2Service oAuth2Service;

//    @GetMapping("/{provider}")
    @PostMapping("/oauth2/callback/google")
    public CustomResponse<MemberResponseDTO.OAuthResponseDTO> oAuth2Login(@Valid @RequestBody OAuth2DTO.OAuth2AccessCodeDTO dto) {
        return CustomResponse.onSuccess(oAuth2Service.login("google", dto.getCode()));
    }
}
