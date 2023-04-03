package ru.gubernik.rails.railbot.web.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class WebWorkRequest {

    private String employeeLogin;

    private List<WebWorkData> workData;
}
