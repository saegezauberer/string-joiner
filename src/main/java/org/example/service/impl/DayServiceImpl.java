package org.example.service.impl;

import org.example.service.DayService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DayServiceImpl implements DayService {

    @Override
    public String getCurrentDay() {
        return LocalDate.now().getDayOfWeek().toString();
    }
}
