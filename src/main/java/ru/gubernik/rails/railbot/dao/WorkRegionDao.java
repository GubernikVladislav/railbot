package ru.gubernik.rails.railbot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gubernik.rails.railbot.model.Work;
import ru.gubernik.rails.railbot.model.WorkRegion;

import java.util.List;

@Repository
public interface WorkRegionDao extends JpaRepository<WorkRegion, Integer> {

    List<WorkRegion> findByWork(Work work);
}
