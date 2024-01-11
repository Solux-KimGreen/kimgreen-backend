package com.kimgreen.backend.config.Authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        String exception = (String)request.getAttribute("exception");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json; charset=UTF-8");

/*        if (EXPIRED.equals(exception)) { //exception이 null인지 확인
            setResponse(response, EXPIRED);
        }
        if (DENIED.equals(exception)) {
            setResponse(response, DENIED);
        }*/

        if(exception.equals(EXPIRED)) {
            setResponse(response,EXPIRED);
        }
        if(exception.equals(DENIED)) {
            setResponse(response,DENIED);
        }



    }

    public void setResponse(HttpServletResponse response,String msg) throws IOException{
        ObjectNode json = new ObjectMapper().createObjectNode();
        json.put("message", msg);
        json.put("code", HttpStatus.UNAUTHORIZED.value());
        String newResponse = new ObjectMapper().writeValueAsString(json);
        response.getWriter().write(newResponse);
    }
}
