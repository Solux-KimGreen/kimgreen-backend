package com.kimgreen.backend.domain.community.controller;

import com.kimgreen.backend.domain.community.dto.report.ReportRequestDto;
import com.kimgreen.backend.domain.community.service.ReportService;
import com.kimgreen.backend.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.kimgreen.backend.response.Message.*;
import static com.kimgreen.backend.response.Response.*;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "Report")
@RestController
@RequestMapping(value="/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;


    @Operation(summary = "게시글 신고하기")
    @ResponseStatus(OK)
    @PostMapping()
    public Response postReport(@ParameterObject Long postId,
                               @RequestBody ReportRequestDto reportRequestDto) {
        reportService.postReport(postId,reportRequestDto);
        return success(POST_REPORT_SUCCESS);
    }

}