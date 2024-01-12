package com.kimgreen.backend.domain.profile.dto.Badge;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileBadgeRequestDto {
    private List<String> badge;
}
