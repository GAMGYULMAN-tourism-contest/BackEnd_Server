package com.example.gamgyulman.domain.member.controller;

import com.example.gamgyulman.domain.member.dto.MemberResponseDTO;
import com.example.gamgyulman.domain.member.service.OAuth2Service;
import com.example.gamgyulman.global.apiPayload.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/login/oauth2")
@RequiredArgsConstructor
public class OAuth2Controller {

    private final OAuth2Service oAuth2Service;

//    @GetMapping("/{provider}")
    @GetMapping("/oauth2/callback/google")
    public CustomResponse<MemberResponseDTO.OAuthResponseDTO> oAuth2Login(
//            @PathVariable String provider,
            @RequestParam String code) {
        return CustomResponse.onSuccess(oAuth2Service.login("google", code));
    }
}
