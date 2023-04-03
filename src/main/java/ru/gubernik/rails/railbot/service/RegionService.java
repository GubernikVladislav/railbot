package ru.gubernik.rails.railbot.service;

import ru.gubernik.rails.railbot.model.Region;

import java.util.List;

/**
 * Интерфейс для работы с участками
 */
public interface RegionService {

    /**
     * Получение всех участков
     *
     * @return данные всех участков
     */
    List<Region> getAllRegions();

    /**
     * Получение списка учатсков по номерам
     *
     * @param numbers номера искомых учатсков
     * @return список участков
     */
    List<Region> getByNumbers(List<Integer> numbers);
}
