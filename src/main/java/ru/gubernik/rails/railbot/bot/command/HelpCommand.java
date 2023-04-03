package ru.gubernik.rails.railbot.bot.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Collections;
import java.util.List;

@Component
public class HelpCommand implements BotCommand {

    @Override
    public List<SendMessage> runCommand(Message message) {

        SendMessage helpMessage = new SendMessage();
        helpMessage.setChatId(message.getChat().getId().toString());
        helpMessage.setText("""
                Доступные команды:
                /my_work - получение списка назначенных смен
                /request_work - запросить назначение смен
                /start_work - открытие смены
                /region_status - фиксация состояния участка
                /end_work - закрытие смены
                """);
        setDefaultKeyboard(helpMessage);

        return Collections.singletonList(helpMessage);
    }

    private void setDefaultKeyboard(SendMessage response) {

        InlineKeyboardButton requestWork = InlineKeyboardButton.builder()
                .text("Запросить назначение смен.")
                .callbackData("/request_work")
                .build();

        InlineKeyboardButton myWork = InlineKeyboardButton.builder()
                .text("Мой график работы")
                .callbackData("/my_work")
                .build();

        InlineKeyboardButton startWork = InlineKeyboardButton.builder()
                .text("Открыть смену.")
                .callbackData("/start_work")
                .build();

        InlineKeyboardButton regionState = InlineKeyboardButton.builder()
                .text("Отметить статус участка.")
                .callbackData("/region_status")
                .build();

        InlineKeyboardButton endWork = InlineKeyboardButton.builder()
                .text("Закрыть смену.")
                .callbackData("/end_work")
                .build();

        InlineKeyboardMarkup keyboardMarkup = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(requestWork))
                .keyboardRow(List.of(myWork))
                .keyboardRow(List.of(startWork))
                .keyboardRow(List.of(regionState))
                .keyboardRow(List.of(endWork))
                .build();

        response.setReplyMarkup(keyboardMarkup);
    }

    @Override
    public String supportedCommand() {
        return "/help";
    }
}
