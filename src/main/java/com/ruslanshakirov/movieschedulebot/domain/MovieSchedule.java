package com.ruslanshakirov.movieschedulebot.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Builder
@Data
@ToString
public class MovieSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String time;
    private String movieName;
    private String movieDescriptionUrl;
    private String limitations;
    @Embedded
    private Ticket ticket;





}
