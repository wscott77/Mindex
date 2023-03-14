package com.mindex.challenge;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.dao.EmployeeSalaryRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.Salary;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataBootstrapTest2 {

    @Autowired
    private EmployeeSalaryRepository employeeSalaryRepository;

    @Test
    public void test() {
        Salary employee = employeeSalaryRepository.findByEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f");

        assertNotNull(employee);
        assertEquals("16a596ae-edd3-4847-99fe-c4518e82c86f", employee.getEmployeeId());
        assertEquals(100000.0, employee.getSalary(), 0);
        assertEquals("2023-01-01", employee.getEffectiveDate().toString());
    }
}