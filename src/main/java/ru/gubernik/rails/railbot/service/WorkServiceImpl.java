package ru.gubernik.rails.railbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gubernik.rails.railbot.dao.WorkDao;
import ru.gubernik.rails.railbot.model.Work;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkServiceImpl implements WorkService {

    private final WorkDao workDao;

    @Override
    @Transactional(readOnly = true)
    public List<Work> getEmployeeWork(String login) {
        return workDao.findAllByEmployeeLogin(login);
    }

    @Override
    public void startWork(String login) {

    }
}
