package com.ruslanshakirov.movieschedulebot.parser;

import com.ruslanshakirov.movieschedulebot.domain.MovieSchedule;
import com.ruslanshakirov.movieschedulebot.domain.Ticket;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component()
public class KinoKzScheduleScrapper implements MovieScheduleScrapper<MovieSchedule> {

    @Override
    public List<MovieSchedule> scrapeUrl(String url) {
        List<MovieSchedule> movieSchedules = new ArrayList<>();
        try {
            Document document = Jsoup.connect(url).get();
            Elements elements = document.select("tr.seance_active");
            for (Element element : elements) {
                String date = element.select("td:eq(0)").text();
                String time = extractTime(date);
                Elements movieNameAndUrl = element.select("td:eq(1) strong a");
                String movieName = movieNameAndUrl.text();
                String movieDescriptionUrl = KinoKzUrls.KINO_SITE + movieNameAndUrl.attr("href");
                String limitations = element.select("td:eq(4) span").text();
                String adultPrice = element.select("td:eq(5)").text();
                String kidPrice = element.select("td:eq(6)").text();
                String studentPrice = element.select("td:eq(7)").text();
                Ticket ticket = new Ticket(adultPrice, studentPrice, kidPrice);
                MovieSchedule movieSchedule = MovieSchedule.builder().time(time).movieName(movieName).
                        movieDescriptionUrl(movieDescriptionUrl).limitations(limitations).build();
                movieSchedules.add(movieSchedule);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return movieSchedules;
    }

    public static void main(String[] args) {
        System.out.println(new KinoKzScheduleScrapper().extractTime("2017-09-23 10:10:00.0"));
    }

    private String extractTime(String date) {
        Pattern pattern = Pattern.compile("([0-9]{2}:[0-9]{2})");
        Matcher matcher = pattern.matcher(date);
        matcher.find();
        return matcher.group(1);
    }
}
