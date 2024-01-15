package com.kimgreen.backend.domain.notification.controller;

import com.kimgreen.backend.domain.notification.service.NotificationService;
import com.kimgreen.backend.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import static com.kimgreen.backend.response.Message.*;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "notification")
@RestController
@AllArgsConstructor
@RequestMapping(value="/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "푸쉬알림 내역 보기")
    @ResponseStatus(OK)
    @GetMapping()
    public Response getNotification(@ParameterObject Pageable pageable) {
        return Response.success(SUCCESS_GET_NOTIFICATION,notificationService.getNotifications(pageable));
    }
}
