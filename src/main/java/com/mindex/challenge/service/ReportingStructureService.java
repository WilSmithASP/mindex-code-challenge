package com.mindex.challenge.service;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;

import java.util.List;
import java.util.Map;

public interface ReportingStructureService {

    ReportingStructure getReportingStructure(String id);

    int getDirectReports(String id, Map<String, Employee> employeeMap);
}
