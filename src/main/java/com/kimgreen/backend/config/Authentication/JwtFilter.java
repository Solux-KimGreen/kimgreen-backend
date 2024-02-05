package com.kimgreen.backend.config.Authentication;

import com.kimgreen.backend.exception.BlankToken;
import com.kimgreen.backend.exception.MalformedToken;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
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

import javax.print.DocFlavor;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private static final String SUCCESS = "success";
    private static final String EXPIRED = "expired";
    private static final String DENIED = "denied";
    private static final String MALFORMED = "malformed";
    private static final String MALFORMED_JWT = "malformed_jwt";
    private static final String BLANK = "blank";
    private final JwtProvider jwtProvider;
    private final static String[] AUTH_WHITE_LIST_IGNORE = {
            "/swagger-ui/index.html"
            ,"/swagger-ui.html"
            ,"/swagger-ui/**"
            ,"/api-docs/**"
            ,"/v3/api-docs/**"
            ,"/auth/sign-up"
            ,"/auth/log-in"
            ,"/auth/reissue"
    };

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return org.apache.commons.lang3.StringUtils.startsWithAny(request.getRequestURI(),AUTH_WHITE_LIST_IGNORE);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response
            , FilterChain filterChain) throws ServletException, IOException {
        System.out.println("doing jwtFilter");
        try {
            String accessToken = jwtProvider.resolveToken(request, HttpHeaders.AUTHORIZATION);
            if(accessToken.equals(MALFORMED)) {
                throw new MalformedToken();
            } else if(accessToken.equals(BLANK)) {
                throw new BlankToken();
            }
            Authentication authentication = jwtProvider.getAuthentication(accessToken);

            // access token 검증
            if (StringUtils.hasText(accessToken) && jwtProvider.validateToken(accessToken).equals(SUCCESS)) {
                SecurityContextHolder.getContext().setAuthentication(authentication); // security context에 인증 정보 저장
            }
        } catch (ExpiredJwtException e) {
            //throw JwtException
            request.setAttribute("exception",EXPIRED);
        } catch (IllegalArgumentException  e) {
            //throw JwtException
            request.setAttribute("exception",DENIED);
        } catch (MalformedToken e) {
            request.setAttribute("exception",MALFORMED);
        } catch (BlankToken e) {
            System.out.println("catch blank token");
            request.setAttribute("exception",BLANK);
        } catch (MalformedJwtException e) {
            request.setAttribute("exception",MALFORMED_JWT);
        }
        filterChain.doFilter(request, response);
        }

}





