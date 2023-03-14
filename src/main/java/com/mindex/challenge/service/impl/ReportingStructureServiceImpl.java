package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    private final EmployeeService employeeService;

    @Autowired
    public ReportingStructureServiceImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public ReportingStructure getDirectReports(String id) {
        List<Employee> directReports = new ArrayList<>();
        Employee            employee = employeeService.read( id );

        for( Employee e : employee.getDirectReports() ){
            directReports.add( employeeService.read( e.getEmployeeId() ) );
        }
        return new ReportingStructure(employee, directReports.size(), directReports );
    }

    @Override
    public ReportingStructure getAllReports(String id) {
        List<Employee>  allReports = new ArrayList<>();
        Deque<Employee>  employees = new ArrayDeque<>();
        Employee          employee = employeeService.read( id );

        employees.add(employee);
        while( ! employees.isEmpty() ) {
            Employee     currentEmployee = employees.poll();
            List<Employee> directReports = currentEmployee.getDirectReports();

            for( Employee e : directReports){
                e = employeeService.read( e.getEmployeeId() );
                allReports.add(e);
                employees.add(e);
            }
        }

        return new ReportingStructure(employee, allReports.size(), allReports );
    }

}
