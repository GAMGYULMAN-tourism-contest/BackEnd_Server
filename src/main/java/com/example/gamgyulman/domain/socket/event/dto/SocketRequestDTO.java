package com.example.gamgyulman.domain.socket.event.dto;

import lombok.Getter;

public class SocketRequestDTO {

    @Getter
    public static class DeleteSocketDTO {
        private Long scheduleId;
        private Long eventId;
    }
}
