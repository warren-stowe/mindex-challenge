package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private CompensationRepository compensationRepository;

    @Autowired
    private EmployeeServiceImpl employeeService;

    @Override
    public Compensation create(Compensation compensation) {
        LOG.debug("Adding Compensation for new employee: [{}]", compensation);

        Employee newEmployee = employeeService.create(compensation.getEmployee());
        compensation.getEmployee().setEmployeeId(newEmployee.getEmployeeId());

        compensationRepository.save(compensation);

        return compensation;
    }

    @Override
    public Compensation update(Compensation newCompensation) {
        LOG.debug("Updating Compensation for existing employee: [{}]", newCompensation.getEmployee().getEmployeeId());

        Employee employee = employeeService.read(newCompensation.getEmployee().getEmployeeId());
        newCompensation.setEmployee(employee);

        if (newCompensation.getEffectiveDate() == null) { newCompensation.setEffectiveDate(LocalDate.now()); }

        return compensationRepository.save(newCompensation);
    }

    @Override
    public List<Compensation> getCompensationByEmployeeId(String employeeId) {
        return compensationRepository.findByEmployeeId(employeeId);
    }
}
