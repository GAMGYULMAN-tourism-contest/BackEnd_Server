package com.example.gamgyulman.domain.member.controller;

import com.example.gamgyulman.domain.member.annotation.AuthenticatedMember;
import com.example.gamgyulman.domain.member.dto.InvitationRequestDTO;
import com.example.gamgyulman.domain.member.dto.InvitationResponseDTO;
import com.example.gamgyulman.domain.member.entity.Invitation;
import com.example.gamgyulman.domain.member.entity.Member;
import com.example.gamgyulman.domain.member.entity.enums.InvitationStatus;
import com.example.gamgyulman.domain.member.service.command.InvitationCommandService;
import com.example.gamgyulman.domain.member.service.query.InvitationQueryService;
import com.example.gamgyulman.global.apiPayload.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "초대 API")
@RequestMapping("/invitations")
public class InvitationController {

    private final InvitationCommandService invitationCommandService;
    private final InvitationQueryService invitationQueryService;

    // 초대 생성
    @PostMapping
    @Operation(summary = "초대 보내기", description = "초대 보내는 API")
    public CustomResponse<InvitationResponseDTO.InvitationInfoDTO> createInvitation(@AuthenticatedMember Member member,
                                                                                    @Valid @RequestBody InvitationRequestDTO.CreateInvitationDTO dto) {
        Invitation invitation = invitationCommandService.createInvitation(member, dto);
        return CustomResponse.onSuccess(InvitationResponseDTO.InvitationInfoDTO.from(invitation));
    }

    // 승인, 거절 로직
    @PatchMapping
    @Operation(summary = "초대 수락 or 거절", description = "초대 수락 혹은 거절하는 API")
    public CustomResponse<InvitationResponseDTO.InvitationInfoDTO> updateInvitationState(@Valid @RequestBody InvitationRequestDTO.UpdateInvitationStatusDTO dto) {
        Invitation invitation = invitationCommandService.updateInvitation(dto);
        return CustomResponse.onSuccess(InvitationResponseDTO.InvitationInfoDTO.from(invitation));
    }

    @GetMapping("/count")
    @Operation(summary = "읽지 않은 초대 개수 가져오기", description = "읽지 않은 초대의 개수를 가져오는 API")
    public CustomResponse<Long> getCountOfInvitation(@AuthenticatedMember Member member) {
        return CustomResponse.onSuccess(invitationQueryService.getCountOfInvitationNotRead(member));
    }

    // 초대 목록 다 가져오기 command update
    @GetMapping
    @Operation(summary = "초대 목록 가져오기", description = "초대 목록 가져오는 API")
    public CustomResponse<InvitationResponseDTO.InvitationInfoListDTO> getInvitations(@AuthenticatedMember Member member,
                                                                                      @RequestParam(required = false) Long cursor,
                                                                                      @RequestParam(required = false, defaultValue = "10") int offset) {
        Slice<Invitation> invitations = invitationQueryService.getInvitations(member, cursor, offset);
        invitationCommandService.updateStatus(
                invitations.getContent().stream().filter(invitation -> invitation.getStatus().equals(InvitationStatus.NOT_READ)).toList(),
                InvitationStatus.READ
        );
        return CustomResponse.onSuccess(InvitationResponseDTO.InvitationInfoListDTO.from(invitations));
    }

    // 초대 삭제
    @DeleteMapping("/{invitationId}")
    @Operation(summary = "초대 삭제", description = "초대 삭제하는 API")
    public CustomResponse<Long> deleteInvitation(@PathVariable Long invitationId) {
        invitationCommandService.deleteInvitation(invitationId);
        return CustomResponse.onSuccess(invitationId);
    }

    //
}
