package ru.gubernik.rails.railbot.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gubernik.rails.railbot.bot.command.response.RequestWorkResponse;
import ru.gubernik.rails.railbot.model.Employee;
import ru.gubernik.rails.railbot.model.Region;
import ru.gubernik.rails.railbot.model.Work;
import ru.gubernik.rails.railbot.service.EmployeeService;
import ru.gubernik.rails.railbot.service.RegionService;
import ru.gubernik.rails.railbot.web.model.WebWorkData;
import ru.gubernik.rails.railbot.web.model.WebWorkRequest;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final EmployeeService employeeService;
    private final RegionService regionService;
    private final RequestWorkResponse requestWorkResponse;

    @Override
    @Transactional
    public void createWork(WebWorkRequest request) {
        Employee employee = employeeService.getEmployeeByLogin(request.getEmployeeLogin());

        List<Work> employeeWorkList = employee.getWorkList();

        for (WebWorkData workData : request.getWorkData()) {
            Work newWork = new Work();
            newWork.setEmployee(employee);
            newWork.setWorkDate(workData.getWorkDate());

            List<Region> workRegions = regionService.getByNumbers(workData.getRegions());
            newWork.setRegions(Set.copyOf(workRegions));

            employeeWorkList.add(newWork);
        }

        employeeService.update(employee);
        requestWorkResponse.sendResponse(employee.getChatId());
    }
}
