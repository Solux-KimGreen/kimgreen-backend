package com.kimgreen.backend.domain.profile.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Calendar")
@RestController
@RequestMapping(value="/calendar")
public class calendarController {
}
