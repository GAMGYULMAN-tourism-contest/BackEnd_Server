package com.example.gamgyulman.domain.socket.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.user.SimpSubscription;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SocketParticipantServiceImpl implements SocketParticipantService {

    private final SimpUserRegistry simpUserRegistry;

    @Override
    public List<String> getSubscribeMemberList(Long scheduleId) {
        Set<SimpSubscription> set = simpUserRegistry.findSubscriptions(sub -> sub.getDestination().equals("/user/schedules/create/" + scheduleId));
        return set.stream().map(simpSubscription -> simpSubscription.getSession().getUser().getName()).toList();
    }
}
