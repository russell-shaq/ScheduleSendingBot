package com.ruslanshakirov.movieschedulebot;

import com.ruslanshakirov.movieschedulebot.service.MovieScheduleSendingBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;


@SpringBootApplication
@EnableScheduling
public class MovieScheduleBotApplication implements CommandLineRunner {
	@Autowired
	private MovieScheduleSendingBot movieScheduleSendingBot;
	static {
		ApiContextInitializer.init();
	}

	public static void main(String[] args) {
		SpringApplication.run(MovieScheduleBotApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		try {
			telegramBotsApi.registerBot(movieScheduleSendingBot);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}

	}
}
