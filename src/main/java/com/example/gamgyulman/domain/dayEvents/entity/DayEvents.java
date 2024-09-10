package com.example.gamgyulman.domain.dayEvents.entity;

import com.example.gamgyulman.domain.event.entity.Event;
import com.example.gamgyulman.domain.schedule.entity.Schedule;
import com.example.gamgyulman.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class DayEvents extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "day_event_id")
    private Long id;

    @Column(name = "day")
    private int day; // 몇일차

    @Column(name = "date")
    private LocalDate date;

    @OneToMany(mappedBy = "dayEvents", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public void updateDate(LocalDate date) {
        this.date= date;
    }
}
