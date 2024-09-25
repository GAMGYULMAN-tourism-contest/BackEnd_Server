package com.example.gamgyulman.domain.event.service.socket;

import java.security.Principal;
import java.util.Map;

public interface EventSocketService {
    void createEvent(Principal principal, Map<String, Object> requestBody);
    void updateEvent(Principal principal, Map<String, Object> requestBody);
    void deleteBody(Principal principal, Map<String, Object> requestBody);

}
