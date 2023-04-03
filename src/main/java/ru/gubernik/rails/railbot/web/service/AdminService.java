package ru.gubernik.rails.railbot.web.service;

import ru.gubernik.rails.railbot.web.model.WebWorkRequest;

/**
 * Интерфейс для инструментов администратора
 */
public interface AdminService {

    /**
     * Назначение смен сотруднику
     *
     * @param request данные смен
     */
    void createWork(WebWorkRequest request);
}
