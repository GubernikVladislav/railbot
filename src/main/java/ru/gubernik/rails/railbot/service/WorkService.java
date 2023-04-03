package ru.gubernik.rails.railbot.service;

import ru.gubernik.rails.railbot.model.Work;

import java.util.List;

/**
 * Сервис для работы с рабочими сменами
 */
public interface WorkService {

    /**
     * Получение списка рабочих смен по логину
     *
     * @param login логин сотрудника
     * @return список смен сотрудника
     */
    List<Work> getEmployeeWork(String login);

    /**
     * Открыть смену
     *
     * @param login логин сотрудника
     */
    void startWork(String login);
}
