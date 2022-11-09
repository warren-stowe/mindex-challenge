package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CompensationController {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;

    @PostMapping("/compensation")
    public Compensation create(@RequestBody Compensation compensation) {
        LOG.debug("Received request to create compensation for new employee [{}]",
                compensation.getEmployee().getFirstName());

        return compensationService.create(compensation);
    }

    @PutMapping("/compensation")
    public Compensation update(@RequestBody Compensation compensation) {
        LOG.debug("Received request to update compensation for existing employee [{}]",
                compensation.getEmployee().getEmployeeId());

        return compensationService.update(compensation);
    }

    @GetMapping("/compensation/{id}")
    public List<Compensation> read(@PathVariable String id) {
        LOG.debug("Received request to read compensation for employee id: [{}]", id);

        return compensationService.getCompensationByEmployeeId(id);
    }
}
