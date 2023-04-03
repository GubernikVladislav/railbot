package ru.gubernik.rails.railbot.bot.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.gubernik.rails.railbot.model.Employee;
import ru.gubernik.rails.railbot.service.EmployeeService;

import java.util.Collections;
import java.util.List;

import static ru.gubernik.rails.railbot.bot.text.MessageConst.HELP_MESSAGE;
import static ru.gubernik.rails.railbot.bot.text.MessageConst.START_MESSAGE;

@Service
@RequiredArgsConstructor
public class StartCommand implements BotCommand {

    private final EmployeeService employeeService;

    @Override
    public List<SendMessage> runCommand(Message message) {

        String chatId = message.getChat().getId().toString();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        String login = message.getChat().getUserName();
        boolean exists = employeeService.checkExistsEmployee(login);

        if (!exists) {
            Employee employee = new Employee(login, chatId);
            employeeService.create(employee);

            buildResponseForNewEmployee(sendMessage);
        } else {
            sendMessage.setText(HELP_MESSAGE.getMessage());
        }

        return Collections.singletonList(sendMessage);
    }

    @Override
    public String supportedCommand() {
        return "/start";
    }

    private void buildResponseForNewEmployee(SendMessage response) {

        InlineKeyboardButton requestWork = InlineKeyboardButton.builder()
                .text("Запросить назначение смен.")
                .callbackData("/request_work")
                .build();

        InlineKeyboardMarkup keyboardMarkup = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(requestWork))
                .build();

        response.setText(START_MESSAGE.getMessage());
        response.setReplyMarkup(keyboardMarkup);
    }
}
