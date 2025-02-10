package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    EmployeeRepository employeeRepository;

    //I attempted to use MongoDBs graphLookup, but was having issues with the recursion being stopped prematurely.
    //I decided to fetch all employees and put them in a HashMap for a fast lookup
    //This method calls the getDirectReports function to recursively traverse the organization tree
    @Override
    public ReportingStructure getReportingStructure(String id){
        //Get employees and build map
        List<Employee> allEmployees = employeeRepository.findAll();
        Map<String, Employee> employeeMap = new HashMap<>();
        for(Employee employee : allEmployees){
            employeeMap.put(employee.getEmployeeId(), employee);
        }

        //Create reporting structure object and set the direct reports as the return value of getDirectReports
        ReportingStructure reportingStructure = new ReportingStructure(employeeMap.get(id));
        reportingStructure.setNumberOfReports(getDirectReports(id, employeeMap));
        return reportingStructure;
    }

    //Recursive function to traverse the organization tree and return the number of direct reports for an employee based on the ID passed
    //Base case is when the employee has no direct reports, return 0
    //If there are direct reports, call the function for each direct report and add the return value to directReports and return in
    @Override
    public int getDirectReports(String id, Map<String, Employee> employeeMap){
        int directReports = 0;
        Employee employee = employeeMap.get(id);
        if(employee.getDirectReports() == null){
            //No direct reports, return zero
            return directReports;
        }

        //For each direct report, increment the counter by one, and then the return value of the recursive call
        for(int i = 0; i < employee.getDirectReports().size(); i++){
            directReports++;
            directReports += getDirectReports(employee.getDirectReports().get(i).getEmployeeId(), employeeMap);
        }

        //Return the direct report counter
        return directReports;
    }

}
