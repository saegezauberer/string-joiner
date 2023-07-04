package org.example.controller;

import org.example.service.DayService;
import org.example.service.StringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.*;

@RestController
public class StringController {

    @Autowired
    DayService dayService;

    @Autowired
    StringService stringService;

    @GetMapping("/string")
    public ResponseEntity<String> getString(@RequestParam(required = false) String value) {

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        List<Callable<String>> callableTasks = new ArrayList<>();
        callableTasks.add(() -> stringService.returnString(value));
        callableTasks.add(() -> dayService.getCurrentDay());

        try {
            List<Future<String>> futures = executorService.invokeAll(callableTasks);

            StringJoiner stringJoiner = new StringJoiner(" ");

            for (Future<String> future : futures) {
                String futureValue = future.get();
                stringJoiner.add(futureValue == null ? "" : futureValue);
            }

            return ResponseEntity.ok(stringJoiner.toString());
        } catch (InterruptedException | ExecutionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
