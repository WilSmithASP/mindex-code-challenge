package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CompensationController {

    @Autowired
    CompensationService compensationService;

    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @GetMapping("/compensation/{id}")
    public Compensation read(@PathVariable String id) {
        LOG.debug("Received compensation read request for id [{}]", id);

        return compensationService.read(id);
    }

    @PutMapping("/compensation/{id}")
    public Compensation update(@PathVariable String id, @RequestBody Compensation compensation) {
        LOG.debug("Received compensation read request for id [{}]", id);

        return compensationService.update(id, compensation.getSalary());
    }

}
