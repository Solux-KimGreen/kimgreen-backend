package com.kimgreen.backend.config.Authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kimgreen.backend.exception.TokenNotValid;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final String SUCCESS = "success";
    private static final String EXPIRED = "expired";
    private static final String DENIED = "denied";
    private static final String MALFORMED = "malformed";
    private static final String BLANK = "blank";
    private static final String MALFORMED_JWT = "malformed_jwt";

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        String exception = (String)request.getAttribute("exception");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json; charset=UTF-8");

        if(exception!=null) {
            if (exception.equals(EXPIRED)) {
                setResponse(response,HttpStatus.UNAUTHORIZED.value(),"토큰이 유효하지 않습니다.");
            }
            if (exception.equals(DENIED)) {
                setResponse(response,HttpStatus.NOT_FOUND.value(), "잘못된 형식의 요청입니다.");
            }
            if (exception.equals(MALFORMED)) {
                setResponse(response,HttpStatus.BAD_REQUEST.value(), "Bearer 형식이 존재하지 않습니다.");
            }
            if(exception.equals(BLANK)) {
                setResponse(response,HttpStatus.BAD_REQUEST.value(), "토큰이 존재하지 않습니다.");
            }if(exception.equals(MALFORMED_JWT)) {
                setResponse(response,HttpStatus.BAD_REQUEST.value(), "잘못된 형식의 토큰입니다.");
            }
        }

    }

    public void setResponse(HttpServletResponse response,int status,String msg) throws IOException{
        ObjectNode json = new ObjectMapper().createObjectNode();
        json.put("status",status);
        json.put("success", false);
        json.put("message", msg);
        String newResponse = new ObjectMapper().writeValueAsString(json);
        response.getWriter().write(newResponse);
    }
}
