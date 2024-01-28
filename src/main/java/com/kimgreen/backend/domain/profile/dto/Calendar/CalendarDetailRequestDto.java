package com.kimgreen.backend.domain.profile.dto.Calendar;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CalendarDetailRequestDto {
    private Long memberId;
    private String date;
}
