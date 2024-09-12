package com.example.gamgyulman.domain.dayEvents.controller;

import com.example.gamgyulman.domain.dayEvents.dto.DayEventsResponseDTO;
import com.example.gamgyulman.domain.dayEvents.entity.DayEvents;
import com.example.gamgyulman.domain.dayEvents.service.query.DayEventsQueryService;
import com.example.gamgyulman.global.apiPayload.CustomResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Day events API")
@RequestMapping("/dayEvents")
public class DayEventsController {

    private final DayEventsQueryService dayEventsQueryService;

    @GetMapping("/schedules/{scheduleId}")
    public CustomResponse<DayEventsResponseDTO.DayEventsInfoListDTO> getAllDayEventsWithSchedule(@PathVariable Long scheduleId) {
        List<DayEvents> dayEvents = dayEventsQueryService.findAllDayEvents(scheduleId);
        return CustomResponse.onSuccess(DayEventsResponseDTO.DayEventsInfoListDTO.from(dayEvents));
    }

    @GetMapping("/{dayEventsId}")
    public CustomResponse<DayEventsResponseDTO.DayEventsInfoDTO> getDayEvents(@PathVariable Long dayEventsId) {
        DayEvents dayEvents = dayEventsQueryService.findDayEvents(dayEventsId);
        return CustomResponse.onSuccess(DayEventsResponseDTO.DayEventsInfoDTO.from(dayEvents));
    }
}
