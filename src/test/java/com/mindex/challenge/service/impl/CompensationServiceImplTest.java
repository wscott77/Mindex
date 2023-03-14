package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.Salary;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

    private String compensationUrl;
    private String compensationIdUrl;

    @Autowired
    private CompensationService compensationService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        compensationUrl   = "http://localhost:" + port + "/employee/compensation";
        compensationIdUrl = "http://localhost:" + port + "/employee/compensation/{id}";
    }

    @Test
    public void testCreateReadUpdate() {
        Employee testEmployee = new Employee();
        testEmployee.setFirstName( "John");
        testEmployee.setLastName(  "Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition(  "Developer");

        Salary employeeSalary = new Salary();
        employeeSalary.setSalary(200000);
        employeeSalary.setEffectiveDate( LocalDate.of(2023,01,10) );

        Compensation compensation = new Compensation( testEmployee, employeeSalary);

        // Create check
        Compensation createdEmployee = restTemplate.postForEntity(compensationUrl, compensation, Compensation.class).getBody();

        assertNotNull(createdEmployee.getEmployee().getEmployeeId());
        assertEmployeeEquivalence(testEmployee, createdEmployee.getEmployee());

        // Read check
        Compensation readEmployee = restTemplate.getForEntity(compensationIdUrl, Compensation.class
                                        , createdEmployee.getEmployee().getEmployeeId()).getBody();

        assertEquals(createdEmployee.getEmployee().getEmployeeId(), readEmployee.getEmployee().getEmployeeId());
        assertEmployeeEquivalence(createdEmployee.getEmployee(), readEmployee.getEmployee());
        assertSalaryEquivalence(  createdEmployee.getSalary()  , readEmployee.getSalary()  );
    }

    private static void assertSalaryEquivalence(Salary expected, Salary actual) {
        assertEquals(expected.getEmployeeId()   , actual.getEmployeeId()      );
        assertEquals(expected.getSalary()       , actual.getSalary(), 0 );
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate()   );
    }
    private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
        assertEquals(expected.getFirstName() , actual.getFirstName() );
        assertEquals(expected.getLastName()  , actual.getLastName()  );
        assertEquals(expected.getDepartment(), actual.getDepartment());
        assertEquals(expected.getPosition()  , actual.getPosition()  );
    }
}
