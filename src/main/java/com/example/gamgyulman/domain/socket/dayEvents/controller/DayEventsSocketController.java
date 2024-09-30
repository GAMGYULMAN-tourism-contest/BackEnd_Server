package com.example.gamgyulman.domain.socket.dayEvents.controller;

import com.example.gamgyulman.domain.socket.dayEvents.service.DayEventsSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class DayEventsSocketController {

    private final DayEventsSocketService dayEventsSocketService;

    @MessageMapping("/dayEvents/create")
    public void createDayEvents(Principal principal, Map<String, Object> payload) {
        dayEventsSocketService.createDayEvents(principal, payload);
    }

    @MessageMapping("/dayEvents/delete")
    public void deleteDayEvents(Principal principal, Map<String, Object> payload) {
        dayEventsSocketService.deleteDayEvents(principal, payload);
        dayEventsSocketService.syncDayEvents(principal, payload);
    }
}
