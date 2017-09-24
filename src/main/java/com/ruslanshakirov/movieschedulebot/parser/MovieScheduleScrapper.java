package com.ruslanshakirov.movieschedulebot.parser;

import java.util.List;

public interface MovieScheduleScrapper<T> {
    public List<T> scrapeUrl(String url);
}
