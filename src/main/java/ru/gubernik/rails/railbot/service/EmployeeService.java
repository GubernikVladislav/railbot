package ru.gubernik.rails.railbot.service;

import ru.gubernik.rails.railbot.model.Employee;

import java.util.List;

public interface EmployeeService {

    /**
     * Добавление нового сотрудника
     *
     * @param employee данные сотрудника
     */
    void create(Employee employee);

    /**
     * Обновление данных сотрудника
     *
     * @param employee данные сотрудника
     */
    void update(Employee employee);

    /**
     * Получение списка администраторов
     *
     * @return список администраторов
     */
    List<Employee> getAdmins();

    /**
     * Получение данных сотрудника по логину
     *
     * @param login логин сотрудника
     * @return данные сотрудника
     */
    Employee getEmployeeByLogin(String login);

    /**
     * Проврека наличия сотрудника по логину
     *
     * @param login логин сотрудника
     * @return true - если сотрудник найден
     */
    boolean checkExistsEmployee(String login);
}
