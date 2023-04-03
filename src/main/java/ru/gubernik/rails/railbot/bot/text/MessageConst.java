package ru.gubernik.rails.railbot.bot.text;

import lombok.Getter;

@Getter
public enum MessageConst {

    START_MESSAGE("""
            Выдобавлены в список сотрудников!
            Для начала работы запросите назначение смен.
            """),
    HELP_MESSAGE("""
            Доступные команды:
            /my_work - получение списка назначенных смен
            /request_work - запросить назначение смен
            /start_work - открытие смены
            /region_status - фиксация состояния участка
            /end_work - закрытие смены
            """),

    REQUEST_EMPLOYEE_MESSAGE("""
            Отправлен запрос на назначение смен. Дождитесь уведомления о готовности.
            """);

    private final String message;

    MessageConst(String message) {
        this.message = message;
    }
}
