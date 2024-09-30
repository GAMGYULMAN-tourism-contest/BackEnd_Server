package com.example.gamgyulman.domain.socket.dayEvents.service;

import com.example.gamgyulman.domain.dayEvents.dto.DayEventsResponseDTO;
import com.example.gamgyulman.domain.dayEvents.entity.DayEvents;
import com.example.gamgyulman.domain.dayEvents.exception.DayEventsErrorCode;
import com.example.gamgyulman.domain.dayEvents.exception.DayEventsException;
import com.example.gamgyulman.domain.dayEvents.repository.DayEventsRepository;
import com.example.gamgyulman.domain.dayEvents.service.command.DayEventsCommandService;
import com.example.gamgyulman.domain.socket.event.service.SocketParticipantService;
import com.example.gamgyulman.domain.member.entity.Member;
import com.example.gamgyulman.domain.member.exception.MemberErrorCode;
import com.example.gamgyulman.domain.member.exception.MemberException;
import com.example.gamgyulman.domain.member.repository.MemberRepository;
import com.example.gamgyulman.domain.schedule.dto.ScheduleResponseDTO;
import com.example.gamgyulman.domain.schedule.entity.Schedule;
import com.example.gamgyulman.domain.schedule.entity.ScheduleParticipant;
import com.example.gamgyulman.domain.schedule.exception.ScheduleErrorCode;
import com.example.gamgyulman.domain.schedule.exception.ScheduleException;
import com.example.gamgyulman.domain.schedule.exception.ScheduleParticipantErrorCode;
import com.example.gamgyulman.domain.schedule.repository.ScheduleParticipantRepository;
import com.example.gamgyulman.domain.schedule.repository.ScheduleRepository;
import com.example.gamgyulman.global.apiPayload.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DayEventsSocketServiceImpl implements DayEventsSocketService {

    private final MemberRepository memberRepository;
    private final ScheduleParticipantRepository scheduleParticipantRepository;
    private final ScheduleRepository scheduleRepository;
    private final DayEventsRepository dayEventsRepository;
    private final DayEventsCommandService dayEventsCommandService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final SocketParticipantService socketParticipantService;

    @Override
    public void createDayEvents(Principal principal, Map<String, Object> payload) {
        Long scheduleId = Long.parseLong(payload.get("scheduleId").toString());
        String email = principal.getName();

        // Schedule 가져오기
        Schedule schedule = null;
        try {
            schedule = scheduleRepository.findById(scheduleId).orElseThrow(() ->
                    new ScheduleException(ScheduleErrorCode.NOT_FOUND));
        } catch (ScheduleException e) {
            simpMessagingTemplate.convertAndSendToUser(email, "/schedules/dayEvents/create/" + scheduleId, CustomResponse.onFailure(ScheduleErrorCode.NOT_FOUND));
            return;
        }

        // 참여자인지 확인
        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
                new MemberException(MemberErrorCode.NOT_FOUND));

        Optional<ScheduleParticipant> optional = scheduleParticipantRepository.findByScheduleIsAndMemberIs(schedule, member);
        if (optional.isEmpty()) {
            simpMessagingTemplate.convertAndSendToUser(email, "/schedules/dayEvents/create/" + scheduleId, CustomResponse.onFailure(ScheduleParticipantErrorCode.NOT_FOUND));
        }

        // 스케줄 기간 업데이트
        schedule.update(schedule.getPeriod() + 1);
        int period = schedule.getPeriod();
        LocalDate endDate = schedule.getEndDate();

        // DayEvents 생성
        DayEvents dayEvents = dayEventsRepository.save(DayEvents.builder().day(period).schedule(schedule).date(endDate).build());
        Map<String, Object> response = Map.of("dayEvents", DayEventsResponseDTO.DayEventsInfoDTO.from(dayEvents), "schedule", ScheduleResponseDTO.SchedulePreviewNoRoleDTO.from(schedule));

        for (String user : socketParticipantService.getSubscribeMemberList(scheduleId)) {
            simpMessagingTemplate.convertAndSendToUser(user, "/schedules/dayEvents/create/" + scheduleId, CustomResponse.onSuccess(response));
        }

    }

    @Override
    public void deleteDayEvents(Principal principal, Map<String, Object> payload) {
        Long scheduleId = Long.parseLong(payload.get("scheduleId").toString());
        Long dayEventsId = Long.parseLong(payload.get("dayEventsId").toString());
        String email = principal.getName();
        try {
            dayEventsRepository.findById(dayEventsId).orElseThrow(() ->
                    new DayEventsException(DayEventsErrorCode.NOT_FOUND));
        } catch (DayEventsException e) {
            simpMessagingTemplate.convertAndSendToUser(email, "/schedules/dayEvents/delete/" + scheduleId, CustomResponse.onFailure(DayEventsErrorCode.NOT_FOUND));
            return;
        }

        Schedule schedule = null;
        try {
            schedule = scheduleRepository.findById(scheduleId).orElseThrow(() ->
                    new ScheduleException(ScheduleErrorCode.NOT_FOUND));
        } catch (ScheduleException e) {
            simpMessagingTemplate.convertAndSendToUser(email, "/schedules/dayEvents/delete/" + scheduleId, CustomResponse.onFailure(ScheduleErrorCode.NOT_FOUND));
            return;
        }

        dayEventsCommandService.deleteDayEvents(dayEventsId);
        schedule.update(schedule.getPeriod() - 1);

//        Map<String, Object> response = Map.of("schedule", ScheduleResponseDTO.SchedulePreviewNoRoleDTO.from(schedule));

//        for (String user : socketParticipantService.getSubscribeMemberList(scheduleId)) {
//            simpMessagingTemplate.convertAndSendToUser(user, "/schedules/dayEvents/delete/" + scheduleId, CustomResponse.onSuccess(response));
//        }
    }

    @Override
    public void syncDayEvents(Principal principal, Map<String, Object> payload) {
        Long scheduleId = Long.parseLong(payload.get("scheduleId").toString());
        String email = principal.getName();

        Schedule schedule = null;
        try {
            schedule = scheduleRepository.findById(scheduleId).orElseThrow(() ->
                    new ScheduleException(ScheduleErrorCode.NOT_FOUND));
        } catch (ScheduleException e) {
            simpMessagingTemplate.convertAndSendToUser(email, "/schedules/dayEvents/delete/" + scheduleId, CustomResponse.onFailure(ScheduleErrorCode.NOT_FOUND));
            return;
        }

        List<DayEvents> dayEventsList = dayEventsRepository.findAllByScheduleIsOrderByDay(schedule);

        LocalDate startDate = schedule.getStartDate();
        int day = 1;
        for (DayEvents dayEvents: dayEventsList) {
            dayEvents.updateDate(day, startDate.plusDays(day - 1));
            day++;
        }

        Map<String, Object> response = Map.of("schedule", ScheduleResponseDTO.SchedulePreviewNoRoleDTO.from(schedule));

        for (String user : socketParticipantService.getSubscribeMemberList(scheduleId)) {
            simpMessagingTemplate.convertAndSendToUser(user, "/schedules/dayEvents/delete/" + scheduleId, CustomResponse.onSuccess(response));
        }
    }
}
