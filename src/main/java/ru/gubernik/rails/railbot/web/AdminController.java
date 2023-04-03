package ru.gubernik.rails.railbot.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gubernik.rails.railbot.web.model.WebWorkRequest;
import ru.gubernik.rails.railbot.web.service.AdminService;

@RestController
@RequestMapping(value = "/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/work")
    public void createEmployeeWork(@RequestBody WebWorkRequest request) {
        adminService.createWork(request);
    }
}
