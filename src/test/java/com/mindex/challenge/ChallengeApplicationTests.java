package com.mindex.challenge;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChallengeApplicationTests {

	private String reportsUrl;
	private String compensationUrl;
	private String lennonId;

	@LocalServerPort
	private int port;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private CompensationService compensationService;

	@Autowired
	private TestRestTemplate restTemplate;

	@Before
	public void setup() {
		reportsUrl = "http://localhost:" + port + "/reports";
		compensationUrl = "http://localhost:" + port + "/compensation";
		lennonId = "16a596ae-edd3-4847-99fe-c4518e82c86f";
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void testReportingStructure() {
		String lennonUrl = reportsUrl + "/" + lennonId;
		ResponseEntity<ReportingStructure> lennonReports = restTemplate.getForEntity(lennonUrl, ReportingStructure.class);

		assertEquals(4, lennonReports.getBody().getNumberOfReports());
	}
}
