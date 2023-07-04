package org.example.service.impl;

import org.example.service.StringService;
import org.springframework.stereotype.Service;

@Service
public class StringServiceImpl implements StringService {

    private String value;

    @Override
    public String returnString(String value) {
        if (value != null) {
            this.value = value;
        }

        return this.value;
    }
}
