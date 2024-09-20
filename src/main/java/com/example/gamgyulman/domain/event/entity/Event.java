package com.example.gamgyulman.domain.event.entity;

import com.example.gamgyulman.domain.dayEvents.entity.DayEvents;
import com.example.gamgyulman.domain.location.entity.Location;
import com.example.gamgyulman.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Event extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "startTime") // "09:00" 형태
    private String startTime; // 시간

    @Column(name = "endTime")
    private String endTime;

    @OneToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "day_events_id")
    private DayEvents dayEvents;

    public void update(String title, String description, String startTime, String endTime, Location location, DayEvents dayEvents) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.dayEvents = dayEvents;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
