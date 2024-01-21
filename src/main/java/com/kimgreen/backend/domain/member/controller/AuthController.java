package com.kimgreen.backend.domain.member.controller;

import com.kimgreen.backend.domain.member.dto.Auth.ChangePasswordDto;
import com.kimgreen.backend.domain.member.dto.Auth.LogInRequestDto;
import com.kimgreen.backend.domain.member.dto.Auth.SignUpRequestDto;
import com.kimgreen.backend.domain.member.dto.Auth.TokenDto;
import com.kimgreen.backend.domain.member.service.AuthService;
import com.kimgreen.backend.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.kimgreen.backend.response.Message.*;
import static com.kimgreen.backend.response.Response.*;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "Auth")
@RestController
@AllArgsConstructor
@RequestMapping(value="/auth")
public class AuthController {

    private final AuthService authService;


    @Operation(summary = "회원가입")
    @ResponseStatus(OK)
    @PostMapping("/sign-up")
    public Response signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        authService.signUp(signUpRequestDto);
        return success(SIGN_UP_SUCCESS);
    }
    @Operation(summary = "로그인")
    @ResponseStatus(OK)
    @PostMapping("/log-in")
    public Response logIn(@RequestBody LogInRequestDto logInRequestDto) {
        TokenDto response = authService.logIn(logInRequestDto);
        return success(LOG_IN_SUCCESS,response);
    }
    @Operation(summary = "액세스 토큰 재발급")
    @ResponseStatus(OK)
    @PostMapping("/reissue")
    public Response reIssue(@RequestBody TokenDto tokenDto) {
        authService.tokenReissue(tokenDto);
        return success(TOKEN_REISSUE_SUCCESS);
    }
    @Operation(summary = "비밀번호 변경")
    @ResponseStatus(OK)
    @PatchMapping("/change-password")
    public Response changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        authService.changePassword(changePasswordDto);
        return success(CHANGE_PASSWORD_SUCCESS);
    }

}