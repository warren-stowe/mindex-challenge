package com.mindex.challenge.service;

import com.mindex.challenge.data.Compensation;

import java.util.List;

public interface CompensationService {
    Compensation create(Compensation compensation);
    Compensation update(Compensation compensation);
    List<Compensation> getCompensationByEmployeeId(String employeeId);
}
