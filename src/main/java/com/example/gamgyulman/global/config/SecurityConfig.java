package com.example.gamgyulman.global.config;

import com.example.gamgyulman.domain.member.handler.CustomSuccessfulHandler;
import com.example.gamgyulman.domain.member.jwt.JwtProvider;
import com.example.gamgyulman.domain.member.service.OAuthUserService;
import com.example.gamgyulman.domain.member.service.UserDetailService;
import com.example.gamgyulman.global.config.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuthUserService oAuthUserService;
    private final CustomSuccessfulHandler customSuccessfulHandler;
    private final JwtProvider jwtProvider;
    private final UserDetailService userDetailService;
    private String[] allowedURL = {"/", "/swagger-ui/**", "/swagger-resources/**","/v3/api-docs/**"};

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(CorsConfig.cors()))
                .authorizeHttpRequests(request -> request
                        .requestMatchers(allowedURL).permitAll()
                        .anyRequest().authenticated())
                .addFilterAfter(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf(CsrfConfigurer::disable)
                .formLogin(FormLoginConfigurer::disable)
                .oauth2Login(oauth -> oauth.loginPage("/login")
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(oAuthUserService))
                        .successHandler(customSuccessfulHandler)
                )
        ;
        return http.build();
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(jwtProvider, userDetailService);
    }
}
