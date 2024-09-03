package com.example.gamgyulman.global.config.filter;

import com.example.gamgyulman.domain.member.exception.JwtErrorCode;
import com.example.gamgyulman.domain.member.exception.JwtException;
import com.example.gamgyulman.domain.member.jwt.JwtProvider;
import com.example.gamgyulman.domain.member.service.UserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserDetailService userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer")) {
            String token = header.split(" ")[1];

            if (jwtProvider.isValid(token)) {
                UserDetails details = userDetailService.loadUserByUsername(jwtProvider.getEmail(token));
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                        details, "", details.getAuthorities()
                ));
            }
            else {
                throw new JwtException(JwtErrorCode.INVALID_TOKEN);
            }
        }
        filterChain.doFilter(request, response);
    }
}
