package com.example.gamgyulman.domain.member.dto;

import lombok.Getter;

public class InvitationRequestDTO {

    @Getter
    public static class CreateInvitationDTO {
        private Long scheduleId;
        private String receiverEmail;
    }

    @Getter
    public static class UpdateInvitationStatusDTO {
        private Long invitationId;
        private String status;
    }
}
