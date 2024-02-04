package com.kimgreen.backend.domain.profile.dto.Calendar;

import com.kimgreen.backend.domain.community.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CalendarDetailResponseDto {
    private int postCount;
    private List<CalendarDetailDto> contents;

    public static CalendarDetailResponseDto toDto(int count, List<CalendarDetailDto> contents) {
        return CalendarDetailResponseDto.builder()
                .postCount(count)
                .contents(contents)
                .build();
    }
}