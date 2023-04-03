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
import ru.gubernik.rails.railbot.model.WorkRegion;
import ru.gubernik.rails.railbot.service.EmployeeService;
import ru.gubernik.rails.railbot.service.WorkRegionService;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EndWorkCommand implements BotCommand {

    private final EmployeeService employeeService;
    private final WorkRegionService workRegionService;

    @Override
    @Transactional
    public List<SendMessage> runCommand(Message message) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChat().getId().toString());

        String login = message.getChat().getUserName();
        Employee employee = employeeService.getEmployeeByLogin(login);

        employee.getWorkList().sort(Comparator.comparing(Work::getWorkDate));
        Work lastWork = employee.getWorkList().get(0);

        if (lastWork.getStartDate() == null) {
            sendMessage.setText("Нельзя закрыть не открытую смену");
        } else {
            List<WorkRegion> workRegions = workRegionService.getByWork(lastWork);
            if (workRegions.stream().anyMatch(workRegion -> workRegion.getState() == null)) {
                sendMessage.setText("Нельзя закрыть смену, не внеся информацию по всем участкам");
            } else {
                lastWork.setEndDate(new Date());
                sendMessage.setText("Смена закрыта.");
            }
        }

        return Collections.singletonList(sendMessage);
    }

    @Override
    public String supportedCommand() {
        return "/end_work";
    }
}
