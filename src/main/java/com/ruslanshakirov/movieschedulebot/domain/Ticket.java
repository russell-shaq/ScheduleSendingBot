package com.ruslanshakirov.movieschedulebot.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Ticket {
    private String adultPrice;
    private String studentPrice;
    private String kidPrice;

    public Ticket() {
    }

    public Ticket(String adultPrice, String studentPrice, String kidPrice) {
        this.adultPrice = adultPrice;
        this.studentPrice = studentPrice;
        this.kidPrice = kidPrice;
    }

    public String getAdultPrice() {
        return adultPrice;
    }

    public void setAdultPrice(String adultPrice) {
        this.adultPrice = adultPrice;
    }

    public String getStudentPrice() {
        return studentPrice;
    }

    public void setStudentPrice(String studentPrice) {
        this.studentPrice = studentPrice;
    }

    public String getKidPrice() {
        return kidPrice;
    }

    public void setKidPrice(String kidPrice) {
        this.kidPrice = kidPrice;
    }
}
