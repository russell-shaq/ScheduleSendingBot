package com.ruslanshakirov.movieschedulebot.service;

import com.ruslanshakirov.movieschedulebot.domain.MovieSchedule;
import com.ruslanshakirov.movieschedulebot.parser.KinoKzUrls;
import com.ruslanshakirov.movieschedulebot.parser.MovieScheduleScrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ScrapperSchedulerImpl implements ScrapperScheduler {

    @Autowired
    private MovieScheduleScrapper<MovieSchedule> movieScheduleScrapper;

    @Autowired
    private MovieScheduleSendingBot movieScheduleSendingBot;

    private List<String> urls = Arrays.asList(KinoKzUrls.MEGA_CINEMA, KinoKzUrls.PLAZA_CINEMA,
            KinoKzUrls.ARSENAL_CINEMA);

    @Scheduled(cron = "0 36 14 * * *", zone = "Asia/Almaty")
    @Override
    public void scrapeAt10Am() {
        for (String url : urls) {
            List<MovieSchedule> movieSchedules = movieScheduleScrapper.scrapeUrl(url);
            movieScheduleSendingBot.sendMovieSchedule(movieSchedules);
        }

    }
}
