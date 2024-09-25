package com.example.gamgyulman.domain.event.controller;

import com.example.gamgyulman.domain.event.service.socket.EventSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class EventSocketController {

    private final EventSocketService eventSocketService;

    @MessageMapping("/create")
    public void createEventWithSocket(Principal principal, Map<String, Object> payload) {
        eventSocketService.createEvent(principal, payload);
    }

    @MessageMapping("/update")
    public void updateEventWithSocket(Principal principal, Map<String, Object> payload) {
        eventSocketService.updateEvent(principal, payload);
    }

    @MessageMapping("/delete")
    public void deleteEventWithSocket(Principal principal, Map<String, Object> payload) {
        eventSocketService.deleteBody(principal, payload);
    }
}
