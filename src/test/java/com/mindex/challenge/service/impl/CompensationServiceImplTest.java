package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;

import com.mindex.challenge.service.CompensationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.ZoneId;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {
    private String compensationUrl;

    @Autowired
    private CompensationService compensationService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        compensationUrl = "http://localhost:" + port + "/compensation/{id}";
    }

    @Test
    public void testCreateReadUpdate() {
        // Read checks
        Compensation johnLennon = restTemplate.getForEntity(compensationUrl, Compensation.class, "16a596ae-edd3-4847-99fe-c4518e82c86f").getBody();
        Compensation paulMccartney = restTemplate.getForEntity(compensationUrl, Compensation.class, "b7839309-3348-463b-a7e3-5de1c168beb3").getBody();
        Compensation ringoStarr = restTemplate.getForEntity(compensationUrl, Compensation.class, "03aa1462-ffa9-4978-901b-7c001562cf6f").getBody();
        Compensation peteBest = restTemplate.getForEntity(compensationUrl, Compensation.class, "62c1084e-6e34-4630-93fd-9153afb65309").getBody();
        Compensation georgeHarrison = restTemplate.getForEntity(compensationUrl, Compensation.class, "c0c2293d-16bd-4603-8e08-638a9d18b22c").getBody();


        assertEquals(200000, johnLennon.getSalary());
        assertEquals(120000, paulMccartney.getSalary());
        assertEquals(150000, ringoStarr.getSalary());
        assertEquals(110000, peteBest.getSalary());
        assertEquals(100000, georgeHarrison.getSalary());


        // Update checks
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Compensation newCompensation = new Compensation(300000);

        Compensation updatedCompensation =
                restTemplate.exchange(compensationUrl,
                        HttpMethod.PUT,
                        new HttpEntity<Compensation>(newCompensation, headers),
                        Compensation.class,
                        "16a596ae-edd3-4847-99fe-c4518e82c86f").getBody();

        assertEmployeeEquivalence(newCompensation, updatedCompensation);
    }

    private static void assertEmployeeEquivalence(Compensation expected, Compensation actual) {
        assertEquals(expected.getSalary(), actual.getSalary());
        assertEquals(expected.getEffectiveDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), actual.getEffectiveDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }
}
