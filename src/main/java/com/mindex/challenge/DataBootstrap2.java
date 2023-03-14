package com.mindex.challenge;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindex.challenge.dao.EmployeeSalaryRepository;
import com.mindex.challenge.data.Salary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Component
public class DataBootstrap2 {
    private static final String DATASTORE_LOCATION = "/static/employee_compensation_database.json";

    @Autowired
    private EmployeeSalaryRepository employeeSalaryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        InputStream inputStream  = this.getClass().getResourceAsStream(DATASTORE_LOCATION);

        Salary[] employeeSalaries = null;

        try {
            employeeSalaries = objectMapper.readValue(inputStream, Salary[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Salary employee : employeeSalaries) {
            employeeSalaryRepository.insert(employee);
        }
    }
}
