package com.kimgreen.backend.domain.member.dto.Auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDto {
    private String passwordToCheck;
    private String newPassword;
}
