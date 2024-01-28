package com.kimgreen.backend.response;


import com.kimgreen.backend.exception.DuplicateEmail;
import com.kimgreen.backend.exception.LogInFailureEmail;
import com.kimgreen.backend.exception.LogInFailurePassword;
import com.kimgreen.backend.exception.RefreshTokenExpired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Advice {

    @ExceptionHandler(DuplicateEmail.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Response DuplicateEmailResponse() {
        return Response.failure(HttpStatus.CONFLICT, "중복 이메일입니다.");
}

    @ExceptionHandler(LogInFailureEmail.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Response loginFailureEmailResponse() {
        return Response.failure(HttpStatus.UNAUTHORIZED, "존재하지 않는 email 입니다.");
    }
    @ExceptionHandler(LogInFailurePassword.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Response loginFailurePasswordResponse() {
        return Response.failure(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
    }

    @ExceptionHandler(RefreshTokenExpired.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Response RefreshTokenExpiredResponse() {
        return Response.failure(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 유효하지 않습니다.");
    }



}
