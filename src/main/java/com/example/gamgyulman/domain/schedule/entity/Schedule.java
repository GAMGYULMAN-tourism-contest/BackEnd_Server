package com.example.gamgyulman.domain.schedule.entity;

import com.example.gamgyulman.domain.dayEvents.entity.DayEvents;
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
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "period")
    private int period;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DayEvents> dayEventsList;

    public void update(String title, String description, int period, LocalDate startDate) {
        this.title = title;
        this.description = description;
        this.period = period;
        this.startDate = startDate;
        this.endDate = startDate.plusDays(period);
    }

    public void update(int period) {
        this.period = period;
        this.endDate = startDate.plusDays(period);
    }
}
