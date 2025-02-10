package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationService.class);

    @Autowired
    EmployeeRepository employeeRepository;

    //I decided to add the compensation to each employee record. Nothing too crazy going on with this code
    @Override
    public Compensation read(String id){
        LOG.debug("Fetching compensation for employee [{}]", id);

        return employeeRepository.findByEmployeeId(id).getCompensation();
    }

    //Since compensation is stored with the employee, we fetch the employee, set the new compensation, and save to the database
    @Override
    public Compensation update(String id, int salary){
        LOG.debug("Updating compensation for employee [{}]", id);

        Compensation compensation = new Compensation(salary);
        Employee employee = employeeRepository.findByEmployeeId(id);
        employee.setCompensation(compensation);
        employeeRepository.save(employee);

        return employee.getCompensation();
    }
}
