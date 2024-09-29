package com.example.gamgyulman.domain.socket.event.service;

import com.example.gamgyulman.domain.event.dto.EventRequestDTO;
import com.example.gamgyulman.domain.event.dto.EventResponseDTO;
import com.example.gamgyulman.domain.socket.event.dto.SocketRequestDTO;
import com.example.gamgyulman.domain.event.entity.Event;
import com.example.gamgyulman.domain.event.exception.EventException;
import com.example.gamgyulman.domain.event.exception.SocketErrorCode;
import com.example.gamgyulman.domain.event.exception.SocketException;
import com.example.gamgyulman.domain.event.service.command.EventCommandService;
import com.example.gamgyulman.domain.member.entity.Member;
import com.example.gamgyulman.domain.schedule.repository.ScheduleParticipantRepository;
import com.example.gamgyulman.domain.schedule.repository.ScheduleRepository;
import com.example.gamgyulman.global.apiPayload.CustomResponse;
import com.example.gamgyulman.global.apiPayload.exception.GeneralException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventSocketServiceImpl implements EventSocketService{

    private final EventCommandService eventCommandService;
    private final ScheduleParticipantRepository scheduleParticipantRepository;
    private final ScheduleRepository scheduleRepository;
    private final SimpUserRegistry simpUserRegistry;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ObjectMapper objectMapper;

    // TODO: 나중에 다른 사람에게 보내도록 수정 필요
    @Override
    public void createEvent(Principal principal, Map<String, Object> requestBody) {
        EventRequestDTO.CreateEventDTO dto = requestBodyToDTO(requestBody, EventRequestDTO.CreateEventDTO.class);
        String userEmail = principal.getName();


        Event event;
        Long scheduleId = Long.parseLong(requestBody.get("scheduleId").toString());
        try {
            event = eventCommandService.createEvent(dto);
        } catch (EventException e) {
            simpMessagingTemplate.convertAndSendToUser(userEmail, "/schedules/create/" + scheduleId, CustomResponse.onFailure(e.getCode()));
            return;
        }

        EventResponseDTO.EventPreviewDTO response = EventResponseDTO.EventPreviewDTO.from(event);

        List<Member> list = scheduleParticipantRepository.findAllByScheduleIs(scheduleId);
        for (Member member: list) {

//            if (!member.getEmail().equals(userEmail)) {
            simpMessagingTemplate.convertAndSendToUser(member.getEmail(), "/schedules/create/" + scheduleId, CustomResponse.onSuccess(response));
//            }
        }
    }

    @Override
    public void updateEvent(Principal principal, Map<String, Object> requestBody) {
        EventRequestDTO.UpdateEventDTO dto = requestBodyToDTO(requestBody, EventRequestDTO.UpdateEventDTO.class);
        String userEmail = principal.getName();

        Event event;
        Long scheduleId = Long.parseLong(requestBody.get("scheduleId").toString());
        try {
            event = eventCommandService.updateEvent(dto);
        } catch (EventException e) {
            simpMessagingTemplate.convertAndSendToUser(userEmail, "/schedules/update/" + scheduleId, CustomResponse.onFailure(e.getCode()));
            return;
        }

        EventResponseDTO.EventPreviewDTO response = EventResponseDTO.EventPreviewDTO.from(event);

        List<Member> list = scheduleParticipantRepository.findAllByScheduleIs(scheduleId);
        for (Member member : list) {
//            if (!member.getEmail().equals(userEmail)) {
            simpMessagingTemplate.convertAndSendToUser(member.getEmail(), "/schedules/update/" + scheduleId, CustomResponse.onSuccess(response));
//            }
        }
    }

    @Override
    public void deleteBody(Principal principal, Map<String, Object> requestBody) {
        String userEmail = principal.getName();
        SocketRequestDTO.DeleteSocketDTO dto = requestBodyToDTO(requestBody, SocketRequestDTO.DeleteSocketDTO.class);

        Long scheduleId = dto.getScheduleId();
        try {
            eventCommandService.deleteEvent(dto.getEventId());
        } catch (GeneralException e) {
            simpMessagingTemplate.convertAndSendToUser(userEmail, "/schedules/delete/" + scheduleId, CustomResponse.onFailure(e.getCode()));
        }

        List<Member> list = scheduleParticipantRepository.findAllByScheduleIs(scheduleId);
        for (Member member : list) {
//            if (!member.getEmail().equals(userEmail)) {
            simpMessagingTemplate.convertAndSendToUser(member.getEmail(), "/schedules/update/" + scheduleId, CustomResponse.onSuccess(dto.getEventId()));
//            }
        }
    }

    private <T> T requestBodyToDTO(Map<String, Object> requestBody, Class<T> cls) {
        try {
            return objectMapper.convertValue(requestBody, cls);
        } catch (Exception e) {
            throw new SocketException(SocketErrorCode.PARSING_ERROR);
        }
    }
}
