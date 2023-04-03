package ru.gubernik.rails.railbot.bot.command;

import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.gubernik.rails.railbot.model.*;
import ru.gubernik.rails.railbot.service.EmployeeService;
import ru.gubernik.rails.railbot.service.WorkRegionService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RegionOkStateCommand implements BotCommand{

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
        regionData.sort(Comparator.comparing(workRegion -> workRegion.getRegion().getNumber()));

        WorkRegion regionToChangeState = regionData.get(0);
        regionToChangeState.setState(RegionState.OK);
        workRegionService.save(regionToChangeState);

        List<WorkRegion> regionsWithoutState = new ArrayList<>(regionData.stream().filter(region -> region.getState() == null).toList());
        regionsWithoutState.sort(Comparator.comparing(region -> region.getRegion().getNumber()));

        sendMessage.setText(buildResponse(sendMessage, regionsWithoutState, regionToChangeState.getRegion().getNumber()));

        return Collections.singletonList(sendMessage);
    }

    private String buildResponse(SendMessage sendMessage, List<WorkRegion> regions, Integer changed) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Изменен статус для участка: №").append(changed).append("\n");
        if (!regions.isEmpty()) {
            stringBuilder.append("Следующий участок: #").append(regions.get(0));
            setRegionKeyboard(sendMessage);
        } else {
            stringBuilder.append("Все участки отмечены, можно закрыть смену");
            setEndWorkKeyboard(sendMessage);
        }


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

    private void setEndWorkKeyboard(SendMessage sendMessage) {

        InlineKeyboardButton endWork = InlineKeyboardButton.builder()
                .text("Закрыть смену")
                .callbackData("/end_work")
                .build();

        InlineKeyboardMarkup keyboardMarkup = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(endWork))
                .build();

        sendMessage.setReplyMarkup(keyboardMarkup);
    }

    @Override
    public String supportedCommand() {
        return "/region_ok";
    }
}
