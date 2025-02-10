package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.ReportingStructure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTests {

    private String reportingStructureUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        reportingStructureUrl = "http://localhost:" + port + "/reportingstructure/{id}";
    }

    @Test
    public void testDirectReports() {
        ReportingStructure johnLennon = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, "16a596ae-edd3-4847-99fe-c4518e82c86f").getBody();
        ReportingStructure paulMccartney = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, "b7839309-3348-463b-a7e3-5de1c168beb3").getBody();
        ReportingStructure ringoStarr = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, "03aa1462-ffa9-4978-901b-7c001562cf6f").getBody();
        ReportingStructure peteBest = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, "62c1084e-6e34-4630-93fd-9153afb65309").getBody();
        ReportingStructure georgeHarrison = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, "c0c2293d-16bd-4603-8e08-638a9d18b22c").getBody();


        //Direct reports checks
        assertEquals(4, johnLennon.getNumberOfReports());
        assertEquals(0, paulMccartney.getNumberOfReports());
        assertEquals(2, ringoStarr.getNumberOfReports());
        assertEquals(0, peteBest.getNumberOfReports());
        assertEquals(0, georgeHarrison.getNumberOfReports());
    }

}
