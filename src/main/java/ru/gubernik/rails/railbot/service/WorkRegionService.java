package ru.gubernik.rails.railbot.service;

import ru.gubernik.rails.railbot.model.Work;
import ru.gubernik.rails.railbot.model.WorkRegion;

import java.util.List;

/**
 * Интерфейс для работы с состоянием участков
 */
public interface WorkRegionService {

    /**
     * Сохранение данных по участку за смену
     *
     * @param workRegion данные участка
     */
    void save(WorkRegion workRegion);

    /**
     * Получение состояния участков по данным рабочей смены
     *
     * @param work данные смены
     * @return состояние участков
     */
    List<WorkRegion> getByWork(Work work);
}
