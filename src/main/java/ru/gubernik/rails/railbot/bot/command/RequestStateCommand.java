package ru.gubernik.rails.railbot.bot.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.gubernik.rails.railbot.model.Employee;
import ru.gubernik.rails.railbot.model.Region;
import ru.gubernik.rails.railbot.model.Work;
import ru.gubernik.rails.railbot.model.WorkRegion;
import ru.gubernik.rails.railbot.service.EmployeeService;
import ru.gubernik.rails.railbot.service.WorkRegionService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RequestStateCommand implements BotCommand {

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

        List<WorkRegion> regionData = workRegionService.getByWork(lastWork);

        List<Region> incorrectRegions = new ArrayList<>(regionData.stream().filter(region -> region.getState() == null).map(WorkRegion::getRegion).toList());
        incorrectRegions.sort(Comparator.comparing(Region::getNumber));

        if (!incorrectRegions.isEmpty()) {
            sendMessage.setText(buildRegionString(incorrectRegions));
        }

        setRegionKeyboard(sendMessage);
        return Collections.singletonList(sendMessage);
    }

    private String buildRegionString(List<Region> regions) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Нет данных для участков: ").append("\n");

        for (Region region : regions) {
            stringBuilder.append("№").append(region.getNumber()).append(" ");
        }
        stringBuilder.append("\n");
        stringBuilder.append("Следующий участок: №").append(regions.get(0).getNumber());

        return stringBuilder.toString();
    }

    private void setRegionKeyboard(SendMessage sendMessage) {

        InlineKeyboardButton regionOk = InlineKeyboardButton.builder()
                .text("Приемлимо")
                .callbackData("/region_ok")
                .build();

        InlineKeyboardButton regionNotOk = InlineKeyboardButton.builder()
                .text("Требует ремонта (не рализовано)")
                .callbackData("/region_ok")
                .build();

        InlineKeyboardMarkup keyboardMarkup = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(regionOk))
                .keyboardRow(List.of(regionNotOk))
                .build();

        sendMessage.setReplyMarkup(keyboardMarkup);
    }

    @Override
    public String supportedCommand() {
        return "/region_status";
    }
}
