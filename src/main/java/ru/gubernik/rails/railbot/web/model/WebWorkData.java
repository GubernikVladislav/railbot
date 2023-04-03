package ru.gubernik.rails.railbot.web.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class WebWorkData {

    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date workDate;

    private List<Integer> regions;
}
