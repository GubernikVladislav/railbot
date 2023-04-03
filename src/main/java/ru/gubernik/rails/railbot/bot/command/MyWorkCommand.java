package ru.gubernik.rails.railbot.bot.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.gubernik.rails.railbot.model.Region;
import ru.gubernik.rails.railbot.model.Work;
import ru.gubernik.rails.railbot.service.WorkService;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MyWorkCommand implements BotCommand{

    private final WorkService workService;

    @Override
    @Transactional
    public List<SendMessage> runCommand(Message message) {

        String login = message.getChat().getUserName();
        List<Work> myWork = workService.getEmployeeWork(login);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChat().getId().toString());

        if (myWork.isEmpty()) {
            sendMessage.setText("""
                    Нет назначенных смен! Запросить смены - /request_work
                    """);
            setDefaultKeyboard(sendMessage);
        } else {
            sendMessage.setText(buildWorkString(myWork));
        }

        return Collections.singletonList(sendMessage);
    }

    private String buildWorkString(List<Work> myWork) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Вам назначены смены:\n");

        for (Work work : myWork) {
            stringBuilder.append("Дата: ").append(work.getWorkDate()).append("\n");
            stringBuilder.append("Участки: ");
            Iterator<Region> iterator = work.getRegions().iterator();
            while (iterator.hasNext()) {
                Region region = iterator.next();
                stringBuilder.append("№").append(region.getNumber());
                if (iterator.hasNext()) {
                    stringBuilder.append(", ");
                }
            }
            stringBuilder.append("\n");
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    private void setDefaultKeyboard(SendMessage response) {

        if (response.getReplyMarkup() != null) {
            return;
        }

        InlineKeyboardButton help = InlineKeyboardButton.builder()
                .text("Запросить смены")
                .callbackData("/request_work")
                .build();

        InlineKeyboardMarkup keyboardMarkup = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(help))
                .build();

        response.setReplyMarkup(keyboardMarkup);
    }

    @Override
    public String supportedCommand() {
        return "/my_work";
    }
}
