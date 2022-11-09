package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Creating employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }

    @Override
    public ReportingStructure getEmployeeReportingStructure(String id) {

        Employee employee = employeeRepository.findByEmployeeId(id);

        return new ReportingStructure(employee, countNumberOfReports(id));
    }

    private int countNumberOfReports(String id) {

        int count = 0;
        Queue<String> reports = new PriorityQueue();
        reports.add(id);

        while (!reports.isEmpty()) {

            Employee nextEmployee = employeeRepository.findByEmployeeId(reports.poll());

            if (nextEmployee.getDirectReports() == null || nextEmployee.getDirectReports().size() == 0) {
                continue;
            }

            for (Employee employee : nextEmployee.getDirectReports()) {
                count++;
                reports.add(employee.getEmployeeId());
            }
        }

        return count;
    }
}
