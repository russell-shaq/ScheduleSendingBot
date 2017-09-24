package com.ruslanshakirov.movieschedulebot.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Tolerate;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@Builder
@ToString
public class Subscriber {
    @Id
    private long chatId;
    private String firstName;
    private String lastName;
    private String userName;
    @Tolerate
    public Subscriber() {
    }
}
