package com.kimgreen.backend.config.Authentication;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private static final String SUCCESS = "success";
    private static final String EXPIRED = "expired";
    private static final String DENIED = "denied";
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response
            , FilterChain filterChain) throws ServletException, IOException {
        try {
            String accessToken = jwtProvider.resolveToken(request, HttpHeaders.AUTHORIZATION);
            Authentication authentication = jwtProvider.getAuthentication(accessToken);

            // access token 검증
            if (StringUtils.hasText(accessToken) && jwtProvider.validateToken(accessToken) == SUCCESS) {
                SecurityContextHolder.getContext().setAuthentication(authentication); // security context에 인증 정보 저장
            }
        } catch (ExpiredJwtException e) {
            //throw JwtException
            request.setAttribute("exception",EXPIRED);
        } catch (IllegalArgumentException  e) {
            //throw JwtException
            request.setAttribute("exception",DENIED);
        }
        filterChain.doFilter(request, response);
        }

}





