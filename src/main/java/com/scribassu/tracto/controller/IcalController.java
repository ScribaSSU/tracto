package com.scribassu.tracto.controller;

import com.scribassu.tracto.config.DocSwagger;
import com.scribassu.tracto.dto.web.IcalDto;
import com.scribassu.tracto.service.IcalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.google.gson.JsonObject;

@DocSwagger
@RestController
@RequestMapping("/v1.0/schedule/ical")
public class IcalController {

    private final IcalService icalService;

    @Autowired
    public IcalController(IcalService icalService) {
        this.icalService = icalService;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public IcalDto getCsvSchedule(@RequestBody JsonObject jsonObject) {
        return icalService.generateIcalFile(jsonObject);
    }

}
