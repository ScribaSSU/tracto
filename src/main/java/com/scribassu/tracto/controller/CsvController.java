package com.scribassu.tracto.controller;

import com.scribassu.tracto.service.ParseLessonsAndCreateCsv;
import com.google.gson.JsonObject;
import com.scribassu.tracto.config.DocSwagger;
import com.scribassu.tracto.dto.web.CsvDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@DocSwagger
@RestController
@RequestMapping("/v1.0/csvschedule")
public class CsvController {
    private final ParseLessonsAndCreateCsv parseLessonsAndCreateCsv;

    @Autowired
    public CsvController(ParseLessonsAndCreateCsv parseLessonsAndCreateCsv) {
        this.parseLessonsAndCreateCsv = parseLessonsAndCreateCsv;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public CsvDto getCsvSchedule(@RequestBody JsonObject jsonObject) {
        return parseLessonsAndCreateCsv.generateCsvFile(jsonObject);
    }
}
