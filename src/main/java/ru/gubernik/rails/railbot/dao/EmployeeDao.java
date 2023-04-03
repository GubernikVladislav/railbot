package ru.gubernik.rails.railbot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gubernik.rails.railbot.model.Employee;

import java.util.List;

@Repository
public interface EmployeeDao extends JpaRepository<Employee, Integer> {

    Employee findByLogin(String login);

    List<Employee> findAllByIsAdmin(boolean isAdmin);
}
