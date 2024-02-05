package com.kimgreen.backend.domain.profile.dto.Calendar;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CalendarResponseDto {
    private String date;
    private int postCount;
}
