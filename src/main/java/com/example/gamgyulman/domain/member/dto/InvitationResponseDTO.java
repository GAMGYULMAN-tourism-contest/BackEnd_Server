package com.example.gamgyulman.domain.member.dto;

import com.example.gamgyulman.domain.member.entity.Invitation;
import com.example.gamgyulman.domain.schedule.dto.ScheduleResponseDTO;
import lombok.*;
import org.springframework.data.domain.Slice;

import java.util.List;

public class InvitationResponseDTO {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    public static class InvitationInfoDTO {
        private Long id;
        private ScheduleResponseDTO.SchedulePreviewDTO schedule;
        private MemberResponseDTO.MemberInfoDTO sender;
        private MemberResponseDTO.MemberInfoDTO receiver;
        private String status;

        public static InvitationInfoDTO from(Invitation invitation) {
            return InvitationInfoDTO.builder()
                    .id(invitation.getId())
                    .schedule(ScheduleResponseDTO.SchedulePreviewDTO.from(invitation.getSchedule()))
                    .sender(MemberResponseDTO.MemberInfoDTO.from(invitation.getSender()))
                    .receiver(MemberResponseDTO.MemberInfoDTO.from(invitation.getReceiver()))
                    .status(invitation.getStatus().name())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    public static class InvitationInfoListDTO {
        private List<InvitationInfoDTO> invitations;
        private boolean hasNext;
        private Long cursor;

        public static InvitationInfoListDTO from(Slice<Invitation> invitations) {
            List<InvitationInfoDTO> list = invitations.getContent().stream().map(InvitationInfoDTO::from).toList();
            return InvitationInfoListDTO.builder()
                    .invitations(list)
                    .hasNext(invitations.hasNext())
                    .cursor(list.get(list.size() - 1).getId())
                    .build();
        }
    }
}
