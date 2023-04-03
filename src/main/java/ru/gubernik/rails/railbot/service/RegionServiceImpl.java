package ru.gubernik.rails.railbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gubernik.rails.railbot.dao.RegionDao;
import ru.gubernik.rails.railbot.model.Region;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {

    private final RegionDao regionDao;

    @Override
    public List<Region> getAllRegions() {
        return regionDao.findAll();
    }

    @Override
    public List<Region> getByNumbers(List<Integer> numbers) {
        return regionDao.findAllByNumberIn(numbers);
    }
}
