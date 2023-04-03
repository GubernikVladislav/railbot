package ru.gubernik.rails.railbot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gubernik.rails.railbot.model.Region;

import java.util.List;

@Repository
public interface RegionDao extends JpaRepository<Region, Integer> {

    List<Region> findAllByNumberIn(List<Integer> numbers);
}
