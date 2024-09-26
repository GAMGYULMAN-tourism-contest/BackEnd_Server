package com.example.gamgyulman.domain.event.dto;

import lombok.Getter;

public class SocketRequestDTO {

    @Getter
    public static class DeleteSocketDTO {
        private Long scheduleId;
        private Long eventId;
    }
}
