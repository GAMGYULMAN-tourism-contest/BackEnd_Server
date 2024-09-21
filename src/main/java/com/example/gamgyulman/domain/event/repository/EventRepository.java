package com.example.gamgyulman.domain.event.repository;

import com.example.gamgyulman.domain.dayEvents.entity.DayEvents;
import com.example.gamgyulman.domain.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Event e " +
            "WHERE e.dayEvents = :dayEvents " +
            "AND (" +
            "(:startTime BETWEEN e.startTime AND e.endTime)" +
            "OR (:endTime BETWEEN e.startTime AND e.endTime)" +
            ")")
    boolean existsByDayEventsIsAndStartTimeIsBetweenOrEndTimeIsBetween(@Param("dayEvents") DayEvents dayEvents,
                                                                       @Param("startTime") String startTime,
                                                                       @Param("endTime") String endTime);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Event e " +
            "WHERE e.id != :id AND e.dayEvents = :dayEvents " +
            "AND (e.startTime BETWEEN :startTime AND :endOfStartTime " +
            "OR e.endTime BETWEEN :endTime AND :endOfEndTime)")
    boolean existsByDayEventsIsAndStartTimeIsBetweenOrEndTimeIsBetweenAndIdIsNot(@Param("id") Long id,
                                                                                 @Param("dayEvents") DayEvents dayEvents,
                                                                                 @Param("startTime") String startTime,
                                                                                 @Param("endOfStartTime") String endOfStartTime,
                                                                                 @Param("endTime") String endTime,
                                                                                 @Param("endOfEndTime") String endOfEndTime);
}
