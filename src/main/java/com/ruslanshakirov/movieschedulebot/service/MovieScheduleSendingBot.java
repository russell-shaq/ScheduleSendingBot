package com.ruslanshakirov.movieschedulebot.service;

import com.ruslanshakirov.movieschedulebot.dao.SubscriberDao;
import com.ruslanshakirov.movieschedulebot.domain.MovieSchedule;
import com.ruslanshakirov.movieschedulebot.domain.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.List;

import static com.ruslanshakirov.movieschedulebot.service.BotMessages.*;

@Service
public class MovieScheduleSendingBot extends TelegramLongPollingBot {

    @Autowired
    private SubscriberDao subscriberDao;

    public void sendMovieSchedule(List<MovieSchedule> movieSchedules) {
        Iterable<Subscriber> subscribers = subscriberDao.findAll();
        String schedule = prepareMarkDownSchedule(movieSchedules);
           for (Subscriber subscriber : subscribers) {
            SendMessage message = new SendMessage();
            message.setChatId(subscriber.getChatId());
            message.enableMarkdown(true);
            message.setText(schedule);
            try {
                sendMessage(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private String prepareMarkDownSchedule(List<MovieSchedule> shedules) {
        StringBuilder builder = new StringBuilder();
        shedules.forEach(movieSchedule -> {
            builder.append("Время : ").append(movieSchedule.getTime()).append("\n");
            builder.append("Название : ").append(movieSchedule.getMovieName()).append(("\n"));
            builder.append("Ссылка : ").append(movieSchedule.getMovieDescriptionUrl()).append("\n").append("\n");

        });
        return builder.toString();
    }

    private String prepareHtmlSchedule(List<MovieSchedule> schedules) {
        StringBuilder builder = new StringBuilder();
        builder.append("<table>");
        schedules.forEach(movieSchedule -> {
            builder.append("<tr>");
            builder.append("<td>");
            builder.append(movieSchedule.getTime()).append("</td>").append("</tr>");
            builder.append("<tr>");
            builder.append("<td>");
            builder.append(movieSchedule.getMovieName()).append("</td>").append("</tr>");
            builder.append("<tr>");
            builder.append("<td>");
            builder.append(movieSchedule.getMovieDescriptionUrl()).append("</td>").append("</tr>");
            builder.append("</table></br></br>");
        });
        return builder.toString();

    }

    private void subscribe(Chat chat) {
        Long chatId = chat.getId();
        String firstName = chat.getFirstName();
        String lastName = chat.getLastName();
        String userName = chat.getUserName();
        Subscriber subscriber = Subscriber.builder().chatId(chatId).firstName(firstName).lastName(lastName)
                .userName(userName).build();
        subscriberDao.save(subscriber);
        sendResponseMessage(chatId, SUBSCRIBE_MESSAGE_RESPONSE);
    }

    private void unsubscribe(long chatId) {
        subscriberDao.delete(chatId);
        sendResponseMessage(chatId, UNSUBSCRIBE_MESSAGE_RESPONSE);
    }

    private void sendResponseMessage(long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        KeyboardRow keyboardButtons = new KeyboardRow();
        keyboardButtons.add(SUBSCRIBE_MESSAGE);
        keyboardButtons.add(UNSUBSCRIBE_MESSAGE);
        replyKeyboardMarkup.setKeyboard(Arrays.asList(keyboardButtons));
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onUpdateReceived(Update update) {
       if (update.hasMessage()) {
           Message message = update.getMessage();
           String text = message.getText();
           if (text != null) {
               switch (text) {
                   case SUBSCRIBE_MESSAGE : subscribe(message.getChat());
                   break;
                   case UNSUBSCRIBE_MESSAGE: unsubscribe(message.getChatId());
                   break;
                   default : sendResponseMessage(message.getChatId(), DESCRIPTION_MESSAGE_RESPONSE);
               }
           } else {
               sendResponseMessage(message.getChatId(), DESCRIPTION_MESSAGE_RESPONSE);
           }
       }
    }

    @Override
    public String getBotUsername() {
        return BotConfig.BOT_USER_NAME;
    }

    @Override
    public String getBotToken() {
        return BotConfig.TOKEN;
    }
}
