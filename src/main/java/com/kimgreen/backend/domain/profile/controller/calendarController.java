package com.kimgreen.backend.domain.profile.controller;

import com.kimgreen.backend.domain.member.dto.Auth.SignUpRequestDto;
import com.kimgreen.backend.domain.profile.dto.Calendar.CalendarDetailRequestDto;
import com.kimgreen.backend.domain.profile.service.CalendarService;
import com.kimgreen.backend.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static com.kimgreen.backend.response.Message.*;
import static com.kimgreen.backend.response.Response.success;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "Calendar")
@RestController
@RequestMapping(value="/calendar")
@AllArgsConstructor
public class calendarController {

    private final CalendarService calendarService;
    @Operation(summary = "프로필 달력 불러오기")
    @ResponseStatus(OK)
    @GetMapping("/simple")
    public Response getCalender(@RequestParam("memberId") Long memberId, @RequestParam("date")String date) {
        return success(CALENDAR_SUCCESS,calendarService.getCalendar(memberId, date));
    }

    @Operation(summary = "프로필 달력 상세정보 불러오기")
    @ResponseStatus(OK)
    @GetMapping()
    public Response getCalenderDetails(@RequestBody CalendarDetailRequestDto calendarDetailRequestDto) {
        return success(CALENDAR_DETAILS_SUCCESS,calendarService.getCalendarDetails(calendarDetailRequestDto));
    }
}
