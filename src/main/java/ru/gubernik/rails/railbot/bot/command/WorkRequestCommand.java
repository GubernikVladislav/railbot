package ru.gubernik.rails.railbot.bot.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.gubernik.rails.railbot.model.Employee;
import ru.gubernik.rails.railbot.service.EmployeeService;

import java.util.ArrayList;
import java.util.List;

import static ru.gubernik.rails.railbot.bot.text.MessageConst.REQUEST_EMPLOYEE_MESSAGE;

@Component
@RequiredArgsConstructor
public class WorkRequestCommand implements BotCommand {

    private final EmployeeService employeeService;

    @Override
    public List<SendMessage> runCommand(Message message) {

        SendMessage employeeMessage = new SendMessage();
        employeeMessage.setChatId(message.getChat().getId().toString());
        employeeMessage.setText(REQUEST_EMPLOYEE_MESSAGE.getMessage());

        List<SendMessage> messages = buildAdminsMessages(message);
        messages.add(employeeMessage);

        return messages;
    }

    private List<SendMessage> buildAdminsMessages(Message message) {
        List<SendMessage> messages = new ArrayList<>();
        List<Employee> admins = employeeService.getAdmins();

        for (Employee admin : admins) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(admin.getChatId());
            sendMessage.setText("Сотрудник " + message.getChat().getUserName() + " запросил назначение смен.");
            messages.add(sendMessage);
        }

        return messages;
    }

    @Override
    public String supportedCommand() {
        return "/request_work";
    }
}
