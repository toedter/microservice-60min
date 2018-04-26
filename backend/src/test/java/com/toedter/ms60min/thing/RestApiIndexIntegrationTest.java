package com.toedter.ms60min.thing;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Tag("integration")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestApiIndexIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	@DisplayName("should get API index")
	public void shouldGetApiIndex() throws Exception {
		ResponseEntity<String> response = restTemplate.getForEntity("/api", String.class);
	}
}
