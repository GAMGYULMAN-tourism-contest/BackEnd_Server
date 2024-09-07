package com.example.gamgyulman.domain.member.annotation.resolver;

import com.example.gamgyulman.domain.member.annotation.AuthenticatedMember;
import com.example.gamgyulman.domain.member.entity.Member;
import com.example.gamgyulman.domain.member.jwt.JwtProvider;
import com.example.gamgyulman.domain.member.service.UserDetailService;
import com.example.gamgyulman.domain.member.service.query.MemberQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticatedMemberResolver implements HandlerMethodArgumentResolver {

    private final JwtProvider jwtProvider;
    private final MemberQueryService memberQueryService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticatedMember.class) && parameter.getParameterType().isAssignableFrom(Member.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String header = webRequest.getHeader("Authorization");

        if (header != null) {
            String token = header.split(" ")[1];
            String email = jwtProvider.getEmail(token);
            return memberQueryService.getMember(email);
        }

        return null;
    }
}
