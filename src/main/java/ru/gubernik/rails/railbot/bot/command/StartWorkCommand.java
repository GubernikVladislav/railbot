package ru.gubernik.rails.railbot.bot.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.gubernik.rails.railbot.model.Employee;
import ru.gubernik.rails.railbot.model.Work;
import ru.gubernik.rails.railbot.service.EmployeeService;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StartWorkCommand implements BotCommand {

    private final EmployeeService employeeService;

    @Override
    @Transactional
    public List<SendMessage> runCommand(Message message) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChat().getId().toString());

        String login = message.getChat().getUserName();
        Employee employee = employeeService.getEmployeeByLogin(login);

        employee.getWorkList().sort(Comparator.comparing(Work::getWorkDate));
        Work lastWork = employee.getWorkList().get(0);

        if (lastWork.getStartDate() != null) {
            sendMessage.setText("Смена уже открыта");
        } else {
            lastWork.setStartDate(new Date());
            sendMessage.setText("Выполнено открытие смены за дату: " + lastWork.getWorkDate());
        }

        setRegionKeyboard(sendMessage);
        return Collections.singletonList(sendMessage);
    }

    private void setRegionKeyboard(SendMessage sendMessage) {

        InlineKeyboardButton requestWork = InlineKeyboardButton.builder()
                .text("Внести данные участка")
                .callbackData("/region_status")
                .build();

        InlineKeyboardMarkup keyboardMarkup = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(requestWork))
                .build();

        sendMessage.setReplyMarkup(keyboardMarkup);
    }

    @Override
    public String supportedCommand() {
        return "/start_work";
    }
}
