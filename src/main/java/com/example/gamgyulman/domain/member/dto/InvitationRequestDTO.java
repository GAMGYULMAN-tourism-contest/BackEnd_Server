package com.example.gamgyulman.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

public class InvitationRequestDTO {

    @Getter
    public static class CreateInvitationDTO {
        private Long scheduleId;
        @Schema(defaultValue = "test@email.com")
        private String receiverEmail;
    }

    @Getter
    public static class UpdateInvitationStatusDTO {
        private Long invitationId;
        @Schema(defaultValue = "ACCEPT")
        private String status;
    }
}
