package com.ruslanshakirov.movieschedulebot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

public class BotInitializer {
    @Autowired
    private MovieScheduleSendingBot movieScheduleSendingBot;

    @PostConstruct
    public void botInit() {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(movieScheduleSendingBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
