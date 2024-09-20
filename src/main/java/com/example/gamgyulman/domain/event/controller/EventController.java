package com.example.gamgyulman.domain.event.controller;

import com.example.gamgyulman.domain.event.dto.EventRequestDTO;
import com.example.gamgyulman.domain.event.dto.EventResponseDTO;
import com.example.gamgyulman.domain.event.entity.Event;
import com.example.gamgyulman.domain.event.service.command.EventCommandService;
import com.example.gamgyulman.domain.event.service.query.EventQueryService;
import com.example.gamgyulman.global.apiPayload.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
@Tag(name = "Event API")
public class EventController {

    private final EventQueryService eventQueryService;
    private final EventCommandService eventCommandService;

    @PostMapping
    @Operation(summary = "일정 생성 API", description = "일정 생성하는 API")
    public CustomResponse<EventResponseDTO.EventInfoDTO> createEvent(@Valid @RequestBody EventRequestDTO.CreateEventDTO dto) {
        Event event = eventCommandService.createEvent(dto);
        return CustomResponse.onSuccess(eventQueryService.getEventInfo(event));
    }

    @PutMapping
    @Operation(summary = "일정 수정 API", description = "일정 수정하는 API")
    public CustomResponse<EventResponseDTO.EventInfoDTO> updateEvent(@Valid @RequestBody EventRequestDTO.UpdateEventDTO dto) {
        Event event = eventCommandService.updateEvent(dto);
        return CustomResponse.onSuccess(eventQueryService.getEventInfo(event));
    }

    @DeleteMapping("/{eventId}")
    @Operation(summary = "일정 삭제 API", description = "일정 삭제하는 API")
    @Parameter(name = "eventId", description = "일정 PK")
    public CustomResponse<Long> deleteEvent(@PathVariable Long eventId) {
        eventCommandService.deleteEvent(eventId);
        return CustomResponse.onSuccess(eventId);
    }

    @GetMapping("/{eventId}")
    @Operation(summary = "일정 정보 API", description = "일정 정보 가져오는 API")
    @Parameter(name = "eventId", description = "일정 PK")
    public CustomResponse<EventResponseDTO.EventInfoDTO> getEvent(@PathVariable Long eventId) {
        return CustomResponse.onSuccess(eventQueryService.getEventInfo(eventId));
    }
}
