package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
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

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {

    private String employeeIdUrl;

    @Autowired
    private EmployeeService employeeService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        employeeIdUrl = "http://localhost:" + port + "/employee/{id}/all-reports";
    }

    @Test
    public void testGetEmployeeHasNoReports() {
        String testEmployeeId = "b7839309-3348-463b-a7e3-5de1c168beb3";
        // Read check
        ReportingStructure employeeReports = restTemplate.getForEntity(employeeIdUrl, ReportingStructure.class, testEmployeeId).getBody();
        assertNotNull(employeeReports);

        assertEquals( employeeReports.getNumberOfReports(), 0);
        assertEquals( testEmployeeId, employeeReports.getEmployee().getEmployeeId() );
    }

    @Test
    public void testGetEmployeeHasFourReports() {
        String testEmployeeId = "16a596ae-edd3-4847-99fe-c4518e82c86f";
        HashMap<String,String> expectedReports = new HashMap<>();
        expectedReports.put( "b7839309-3348-463b-a7e3-5de1c168beb3", "Paul McCartney" );
        expectedReports.put( "03aa1462-ffa9-4978-901b-7c001562cf6f", "Ringo Starr"    );
        expectedReports.put( "62c1084e-6e34-4630-93fd-9153afb65309", "Pete Best"      );
        expectedReports.put( "c0c2293d-16bd-4603-8e08-638a9d18b22c", "George Harrison");

        // Read check
        ReportingStructure employeeReports = restTemplate.getForEntity(employeeIdUrl, ReportingStructure.class, testEmployeeId).getBody();
        assertNotNull(employeeReports);

        assertEquals( employeeReports.getNumberOfReports(), 4);
        assertEquals( testEmployeeId, employeeReports.getEmployee().getEmployeeId() );

        // Check reports
        assertCorrectReports( employeeReports, expectedReports );
    }

    @Test
    public void testGetEmployeeHasTwoReports() {
        String testEmployeeId = "03aa1462-ffa9-4978-901b-7c001562cf6f";
        HashMap<String,String> expectedReports = new HashMap<>();
        expectedReports.put( "62c1084e-6e34-4630-93fd-9153afb65309", "Pete Best");
        expectedReports.put( "c0c2293d-16bd-4603-8e08-638a9d18b22c", "George Harrison");

        // Read check
        ReportingStructure employeeReports = restTemplate.getForEntity(employeeIdUrl, ReportingStructure.class, testEmployeeId).getBody();
        assertNotNull(employeeReports);

        assertEquals( employeeReports.getNumberOfReports(), 2);
        assertEquals( testEmployeeId, employeeReports.getEmployee().getEmployeeId() );

        //Check reports
        assertCorrectReports( employeeReports, expectedReports );
    }

    private static void assertCorrectReports( ReportingStructure employeeReports, HashMap<String, String> expectedReports ){
        for( Employee e : employeeReports.getDirectReports() ){
            assertTrue( expectedReports.containsKey( e.getEmployeeId()) );
            assertTrue( expectedReports.get( e.getEmployeeId()).equals( e.getFirstName() + " " + e.getLastName()) );
        }
    }

}
