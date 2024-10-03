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
            "(STR_TO_DATE(:startTime, '%H:%i') > STR_TO_DATE(e.startTime, '%H:%i') AND  STR_TO_DATE(:startTime, '%H:%i') < STR_TO_DATE(e.endTime, '%H:%i'))" +
            "OR (STR_TO_DATE(:endTime, '%H:%i') > STR_TO_DATE(e.startTime, '%H:%i') AND STR_TO_DATE(:startTime, '%H:%i') < STR_TO_DATE(e.endTime, '%H:%i'))" +
            ")")
    boolean existsByDayEventsIsAndStartTimeIsBetweenOrEndTimeIsBetween(@Param("dayEvents") DayEvents dayEvents,
                                                                       @Param("startTime") String startTime,
                                                                       @Param("endTime") String endTime);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Event e " +
            "WHERE e.id != :id AND e.dayEvents = :dayEvents " +
            "AND (" +
            "(STR_TO_DATE(:startTime, '%H:%i') > STR_TO_DATE(e.startTime, '%H:%i') AND  STR_TO_DATE(:startTime, '%H:%i') < STR_TO_DATE(e.endTime, '%H:%i'))" +
            "OR (STR_TO_DATE(:endTime, '%H:%i') > STR_TO_DATE(e.startTime, '%H:%i') AND STR_TO_DATE(:startTime, '%H:%i') < STR_TO_DATE(e.endTime, '%H:%i'))" +
            ")")
    boolean existsByDayEventsIsAndStartTimeIsBetweenOrEndTimeIsBetweenAndIdIsNot(@Param("id") Long id,
                                                                                 @Param("dayEvents") DayEvents dayEvents,
                                                                                 @Param("startTime") String startTime,
                                                                                 @Param("endTime") String endTime);
}
