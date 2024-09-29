package com.example.gamgyulman.domain.socket.event.controller;

import com.example.gamgyulman.domain.socket.event.service.EventSocketService;
import com.example.gamgyulman.domain.socket.event.service.SocketParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class EventSocketController {

    private final EventSocketService eventSocketService;
    private final SocketParticipantService socketParticipantService;

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

    @MessageMapping("/participants")
    public void getScheduleParticipant(Principal principal, Map<String, Object> payload) {
        socketParticipantService.sendSubscribeMemberCount(principal, payload);
    }
}
