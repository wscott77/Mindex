package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeSalaryRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.Salary;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CompensationServiceImpl implements CompensationService {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    private final EmployeeSalaryRepository employeeSalaryRepository;
    private final EmployeeService employeeService;

    @Autowired
    public CompensationServiceImpl(EmployeeSalaryRepository employeeSalaryRepository, EmployeeService employeeService) {
        this.employeeSalaryRepository = employeeSalaryRepository;
        this.employeeService          = employeeService;
    }

    @Override
    public Compensation create(Compensation employee) {
        String id = UUID.randomUUID().toString();

        employeeService.create( employee.getEmployee(), id );

        Salary salary = employee.getSalary();
        salary.setEmployeeId( id );
        employeeSalaryRepository.insert( salary );

        return employee;
    }

    @Override
    public Compensation read(String id) {
        Employee     employee = employeeService.read(id);
        Salary employeeSalary = employeeSalaryRepository.findByEmployeeId(employee.getEmployeeId());

        return new Compensation( employee, employeeSalary);
    }
}
