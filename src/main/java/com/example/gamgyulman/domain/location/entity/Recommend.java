package com.example.gamgyulman.domain.location.entity;

import com.example.gamgyulman.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Recommend extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "mapx")
    private double mapx;

    @Column(name = "mapy")
    private double mapy;

    @Column(name = "contentId")
    private String contentId;

}
