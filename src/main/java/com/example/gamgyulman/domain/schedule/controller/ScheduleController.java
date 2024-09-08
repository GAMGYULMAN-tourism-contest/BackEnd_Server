package com.example.gamgyulman.domain.schedule.controller;

import com.example.gamgyulman.domain.member.annotation.AuthenticatedMember;
import com.example.gamgyulman.domain.member.entity.Member;
import com.example.gamgyulman.domain.schedule.dto.ScheduleRequestDTO;
import com.example.gamgyulman.domain.schedule.dto.ScheduleResponseDTO;
import com.example.gamgyulman.domain.schedule.entity.Schedule;
import com.example.gamgyulman.domain.schedule.service.command.ScheduleCommandService;
import com.example.gamgyulman.global.apiPayload.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
@Tag(name = "스케줄 API")
public class ScheduleController {

    private final ScheduleCommandService scheduleCommandService;

    @PostMapping
    @Operation(summary = "스케줄 생성 API", description = "스케줄을 생성하고 사용자를 OWNER로 설정")
    public CustomResponse<ScheduleResponseDTO.CreatedScheduleDTO> createSchedule(@AuthenticatedMember Member member,
                                                                                 @Valid @RequestBody ScheduleRequestDTO.CreateScheduleDTO dto) {
        Schedule schedule = scheduleCommandService.createSchedule(member, dto);
        return CustomResponse.onSuccess(ScheduleResponseDTO.CreatedScheduleDTO.from(schedule));
    }

    @PatchMapping("/{scheduleId}")
    @Operation(summary = "스케줄 수정 API", description = "스케줄을 수정하는 API, 수정은 주인(OWNER), 관리자(MANAGER)만 가능하다.")
    @Parameter(name = "scheduleId", description = "수정할 스케줄의 PK")
    public CustomResponse<ScheduleResponseDTO.UpdatedScheduleDTO> updateSchedule(@AuthenticatedMember Member member,
                                                                                 @PathVariable("scheduleId") Long scheduleId,
                                                                                 @Valid @RequestBody ScheduleRequestDTO.UpdateScheduleDTO dto) {
        Schedule schedule = scheduleCommandService.updateSchedule(member, scheduleId, dto);
        return CustomResponse.onSuccess(ScheduleResponseDTO.UpdatedScheduleDTO.from(schedule));
    }

    @DeleteMapping("/{scheduleId}")
    @Operation(summary = "스케줄 삭제 API (미완료)", description = "스케줄을 삭제하는 API, 삭제는 주인(OWNER)만 가능")
    public CustomResponse<Long> deleteSchedule(@AuthenticatedMember Member member,
                                               @PathVariable("scheduleId") Long id) {
        scheduleCommandService.deleteSchedule(member, id);
        return CustomResponse.onSuccess(id);
    }
}
