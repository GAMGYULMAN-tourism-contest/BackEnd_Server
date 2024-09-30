package com.example.gamgyulman.domain.socket.dayEvents.service;

import java.security.Principal;
import java.util.Map;

public interface DayEventsSocketService {

    void createDayEvents(Principal principal, Map<String, Object> payload);
    void deleteDayEvents(Principal principal, Map<String, Object> payload);
    void syncDayEvents(Principal principal, Map<String, Object> payload);
}
