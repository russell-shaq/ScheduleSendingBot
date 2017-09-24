package com.ruslanshakirov.movieschedulebot.service;

public class BotMessages {
    public static final String SUBSCRIBE_MESSAGE = "/subscribe";
    public static final String UNSUBSCRIBE_MESSAGE = "/unsubscribe";
    public static final String SUBSCRIBE_MESSAGE_RESPONSE = "Вы подписались на расписание кинотеатров Шымкента." +
            "Каждый день в 10:00 вам будут приходить расписание на день. Чтобы отписаться, напишите "
            + UNSUBSCRIBE_MESSAGE + ".";
    public static final String UNSUBSCRIBE_MESSAGE_RESPONSE = "Вы отписались, расписание больше не будет приходить.";
    public static final String DESCRIPTION_MESSAGE_RESPONSE = "Это телеграмм-бот, который автоматически высылает " +
            "расписание кинотеатров Шымкента. Чтобы подписаться напишите " + SUBSCRIBE_MESSAGE + ".";
    public static final String SUBSCRIBE_KEY = "Подписаться на рассылку.";
    public static final String UNSUBSCRIBE_KEY = "Отписаться от рассылки.";
}
