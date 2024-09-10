package com.example.gamgyulman.domain.schedule.controller;

import com.example.gamgyulman.domain.member.annotation.AuthenticatedMember;
import com.example.gamgyulman.domain.member.entity.Member;
import com.example.gamgyulman.domain.schedule.dto.ScheduleRequestDTO;
import com.example.gamgyulman.domain.schedule.dto.ScheduleResponseDTO;
import com.example.gamgyulman.domain.schedule.entity.Schedule;
import com.example.gamgyulman.domain.schedule.service.command.ScheduleCommandService;
import com.example.gamgyulman.domain.schedule.service.query.ScheduleQueryService;
import com.example.gamgyulman.global.apiPayload.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
@Tag(name = "스케줄 API")
public class ScheduleController {

    private final ScheduleCommandService scheduleCommandService;
    private final ScheduleQueryService scheduleQueryService;

    @PostMapping
    @Operation(summary = "스케줄 생성하기", description = "스케줄 생성하는 API, 스케줄을 생성하고 사용자를 OWNER로 설정")
    public CustomResponse<ScheduleResponseDTO.CreatedScheduleDTO> createSchedule(@AuthenticatedMember Member member,
                                                                                 @Valid @RequestBody ScheduleRequestDTO.CreateScheduleDTO dto) {
        Schedule schedule = scheduleCommandService.createSchedule(member, dto);
        return CustomResponse.onSuccess(ScheduleResponseDTO.CreatedScheduleDTO.from(schedule));
    }

    @PatchMapping("/{scheduleId}")
    @Operation(summary = "스케줄 수정하기", description = "스케줄을 수정하는 API, 수정은 주인(OWNER), 관리자(MANAGER)만 가능하다.")
    @Parameter(name = "scheduleId", description = "수정할 스케줄의 PK")
    public CustomResponse<ScheduleResponseDTO.UpdatedScheduleDTO> updateSchedule(@AuthenticatedMember Member member,
                                                                                 @PathVariable("scheduleId") Long scheduleId,
                                                                                 @Valid @RequestBody ScheduleRequestDTO.UpdateScheduleDTO dto) {
        Schedule schedule = scheduleCommandService.updateSchedule(member, scheduleId, dto);
        return CustomResponse.onSuccess(ScheduleResponseDTO.UpdatedScheduleDTO.from(schedule));
    }

    @DeleteMapping("/{scheduleId}")
    @Operation(summary = "스케줄 삭제하기 (미완료)", description = "스케줄을 삭제하는 API, 삭제는 주인(OWNER)만 가능")
    public CustomResponse<Long> deleteSchedule(@AuthenticatedMember Member member,
                                               @PathVariable("scheduleId") Long id) {
        scheduleCommandService.deleteSchedule(member, id);
        return CustomResponse.onSuccess(id);
    }

    @GetMapping("/members")
    @Operation(summary = "사용자의 스케줄 목록 가져오기", description = "사용자의 스케줄 목록 가져오는 API")
    public CustomResponse<ScheduleResponseDTO.SchedulePreviewListDTO> getSchedulesOfMember(@AuthenticatedMember Member member) {
        List<Schedule> scheduleList = scheduleQueryService.getSchedulesOfMember(member);
        return CustomResponse.onSuccess(ScheduleResponseDTO.SchedulePreviewListDTO.from(scheduleList));
    }

    @GetMapping("/{scheduleId}")
    @Operation(summary = "스케줄 하나 가져오기", description = "스케줄 하나에 대한 정보 가져오는 API")
    public CustomResponse<ScheduleResponseDTO.SchedulePreviewDTO> getSchedule(@PathVariable("scheduleId") Long scheduleId) {
        Schedule schedule = scheduleQueryService.getSchedule(scheduleId);
        return CustomResponse.onSuccess(ScheduleResponseDTO.SchedulePreviewDTO.from(schedule));
    }
}
