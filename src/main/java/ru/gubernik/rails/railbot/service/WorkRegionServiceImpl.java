package ru.gubernik.rails.railbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gubernik.rails.railbot.dao.WorkRegionDao;
import ru.gubernik.rails.railbot.model.Work;
import ru.gubernik.rails.railbot.model.WorkRegion;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkRegionServiceImpl implements WorkRegionService {

    private final WorkRegionDao workRegionDao;

    @Override
    public void save(WorkRegion workRegion) {
        workRegionDao.save(workRegion);
    }

    @Override
    public List<WorkRegion> getByWork(Work work) {
        return workRegionDao.findByWork(work);
    }
}
