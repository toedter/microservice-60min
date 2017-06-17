package com.toedter.ms60min.thing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestApiIndexIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void shouldGetApiIndex() throws Exception {
		ResponseEntity<String> response = restTemplate.getForEntity("/api", String.class);
	}
}
