package ru.gubernik.rails.railbot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gubernik.rails.railbot.model.Work;

import java.util.List;

@Repository
public interface WorkDao extends JpaRepository<Work, Integer> {

    List<Work> findAllByEmployeeLogin(String login);
}
