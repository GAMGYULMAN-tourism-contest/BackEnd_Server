package com.example.gamgyulman.domain.socket.event.service;

import java.util.List;

public interface SocketParticipantService {

    List<String> getSubscribeMemberList(Long scheduleId);
}
