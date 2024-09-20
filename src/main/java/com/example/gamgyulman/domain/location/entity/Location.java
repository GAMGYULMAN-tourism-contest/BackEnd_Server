package com.example.gamgyulman.domain.location.entity;

import com.example.gamgyulman.domain.event.entity.Event;
import com.example.gamgyulman.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Location extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long id;

    @Column(name = "content_id")
    private String contentId;

    @Column(name = "content_type_id")
    private String contentTypeId;

    @OneToOne(mappedBy = "location")
    private Event event;

    public void update(String contentId, String contentTypeId) {
        this.contentId = contentId;
        this.contentTypeId = contentTypeId;
    }
}
