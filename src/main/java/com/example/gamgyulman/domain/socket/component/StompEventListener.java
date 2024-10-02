package com.example.gamgyulman.domain.socket.component;

import com.example.gamgyulman.domain.member.service.query.MemberQueryService;
import com.example.gamgyulman.domain.socket.event.service.SocketParticipantService;
import com.example.gamgyulman.global.apiPayload.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.security.Principal;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StompEventListener {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MemberQueryService memberQueryService;

    // scheduleId, SessionId
    private final Map<String, Set<String>> scheduleParticipants = new HashMap<>();

    // sessionId, User Email
    private final Map<String, String> sessionToUser = new HashMap<>();

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    @EventListener
    public void handleSessionSubscribe(SessionSubscribeEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        Principal principal = headerAccessor.getUser();
        String destination = headerAccessor.getDestination();

        String scheduleId = extractScheduleIdFromDestination(destination);

        if (scheduleId != null && principal != null) {
            scheduleParticipants.computeIfAbsent(scheduleId, k -> new HashSet<>()).add(sessionId);
            sessionToUser.put(sessionId, principal.getName());

            scheduler.schedule(() -> sendParticipantsUpdate(scheduleId), 1, TimeUnit.SECONDS);
        }
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();

        scheduleParticipants.forEach((scheduleId, participants) -> {
            if (participants.remove(sessionId)) {
                sessionToUser.remove(sessionId);
                sendParticipantsUpdate(scheduleId);
            }
        });
    }

    private void sendParticipantsUpdate(String scheduleId) {
        Set<String> sessions = scheduleParticipants.getOrDefault(scheduleId, new HashSet<>());
        Set<String> participants = sessions.stream().map(sessionToUser::get).collect(Collectors.toSet());
        int participantCount = participants.size();

        Map<String, Object> response = Map.of("participants", participantCount, "members", participants.stream().map(memberQueryService::getMember).toList());
        for (String email : participants) {
            simpMessagingTemplate.convertAndSendToUser(email, "/schedules/participants/" + scheduleId, CustomResponse.onSuccess(response));
        }
    }



    private String extractScheduleIdFromDestination(String destination) {
        if (destination != null && destination.startsWith("/user/schedules/participants/")) {
            return destination.substring("/user/schedules/participants/".length());
        }
        return null;
    }

}
