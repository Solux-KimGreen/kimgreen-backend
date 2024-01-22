package com.kimgreen.backend.domain.profile.controller;

import com.kimgreen.backend.domain.member.dto.Auth.SignUpRequestDto;
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
    @Operation(summary = "프로필 달력 불러오기 (농사 잘했는지 불러오는거)")
    @ResponseStatus(OK)
    @GetMapping("/calender")
    public Response getCalender(@RequestParam("memberId") Long memberId, @RequestParam("date")LocalDateTime localDateTime) {

        return success(CALENDAR_SUCCESS);
    }

    @Operation(summary = "프로필 달력 상세정보 불러오기 (글들 불러오는거임)")
    @ResponseStatus(OK)
    @GetMapping("/calender-details")
    public Response getCalenderDetails(@RequestBody SignUpRequestDto signUpRequestDto) {

        return success(CALENDAR_DETAILS_SUCCESS);
    }
}
