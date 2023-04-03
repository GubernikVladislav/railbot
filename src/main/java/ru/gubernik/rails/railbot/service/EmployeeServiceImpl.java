package ru.gubernik.rails.railbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gubernik.rails.railbot.dao.EmployeeDao;
import ru.gubernik.rails.railbot.model.Employee;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDao employeeDao;

    @Override
    public void create(Employee employee) {
        employeeDao.save(employee);
    }

    @Override
    public void update(Employee employee) {
        employeeDao.save(employee);
    }

    @Override
    public List<Employee> getAdmins() {
        return employeeDao.findAllByIsAdmin(true);
    }

    @Override
    public Employee getEmployeeByLogin(String login) {
        return employeeDao.findByLogin(login);
    }

    @Override
    public boolean checkExistsEmployee(String login) {
        Employee employee = employeeDao.findByLogin(login);
        return employee != null;
    }
}
