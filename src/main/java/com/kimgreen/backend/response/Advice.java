package com.kimgreen.backend.response;


import com.kimgreen.backend.exception.*;
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

    @ExceptionHandler(MaxUploadSizeExceeded.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response MaxUploadSizeExceededResponse() {
        return Response.failure(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드 최대 용량을 초과했습니다.: 50MB");
    }

    @ExceptionHandler(BadgeNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response BadgeNotFoundResponse() {
        return Response.failure(HttpStatus.NOT_FOUND,"요청한 뱃지를 찾을 수 없습니다.");
    }

    @ExceptionHandler(PostNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response PostNotFoundResponse() {
        return Response.failure(HttpStatus.NOT_FOUND, "요청한 게시글을 찾을 수 없습니다.");
    }

    @ExceptionHandler(WrongPath.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response WrongPath() {
        return Response.failure(HttpStatus.NOT_FOUND, "잘못된 경로입니다.");
    }

    @ExceptionHandler(LogInRequiredException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response LogInRequiredResponse() {
        return Response.failure(HttpStatus.NOT_FOUND, "토큰이 존재하지 않습니다. 로그인 후 이용해주세요.");
    }

}