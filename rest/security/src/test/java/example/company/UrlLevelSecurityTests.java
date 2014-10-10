/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example.company;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Test cases that verify the URL level of security by using RestTemplate.
 *
 * @author Greg Turnquist
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { Application.class, SecurityConfiguration.class })
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class UrlLevelSecurityTests {

	private String baseUrl;

	@Value("${local.server.port}") private int port;

	@Before
	public void setUp() {

		this.baseUrl = "http://localhost:" + port;
		SecurityContextHolder.clearContext();
	}

	@Test
	public void testUrlSecurityForNoCreds() {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/hal+json");

		HttpEntity<MultiValueMap<String, Object>> baseRequest = new HttpEntity<>(headers);

		RestTemplate rest = new RestTemplate();

		System.out.println("============= GET " + baseUrl);

		ResponseEntity<JsonNode> baseResponse = rest.exchange(baseUrl, HttpMethod.GET, baseRequest, JsonNode.class);
		baseResponse.getHeaders().entrySet().stream()//
				.map(e -> e.getKey() + ": " + e.getValue())//
				.forEach(header -> System.out.println(header));
		assertThat(baseResponse.getHeaders().get("Content-Type"), hasItems("application/hal+json"));
		assertThat(baseResponse.getStatusCode(), equalTo(HttpStatus.OK));
		System.out.println();
		System.out.println(baseResponse.getBody());

		System.out.println("============= POST " + baseUrl + "/employees");

		HttpEntity<String> newEmployee = new HttpEntity<>("{firstName: Saruman, lastName: the White, title: Wizard}",
				headers);

		try {
			rest.exchange(baseUrl + "/employees", HttpMethod.POST, newEmployee, JsonNode.class);
			fail("Expected a security error");
		} catch (HttpClientErrorException e) {
			assertThat(e.getStatusCode(), equalTo(HttpStatus.UNAUTHORIZED));
		}
	}

	@Test
	public void testUrlSecurityForUsers() {

		System.out.println("============= GET " + baseUrl + "/employees");

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/hal+json");

		String userCreds = new String(Base64.encode(("greg:turnquist").getBytes()));
		headers.set("Authorization", "Basic " + userCreds);
		headers.set("Accept", "application/hal+json");

		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(headers);

		RestTemplate rest = new RestTemplate();

		ResponseEntity<JsonNode> employeesResponse = rest.exchange(baseUrl + "/employees", HttpMethod.GET, request,
				JsonNode.class);
		employeesResponse.getHeaders().entrySet().stream()//
				.map(e -> e.getKey() + ": " + e.getValue())//
				.forEach(header -> System.out.println(header));
		assertThat(employeesResponse.getHeaders().get("Content-Type"), hasItems("application/hal+json"));
		assertThat(employeesResponse.getStatusCode(), equalTo(HttpStatus.OK));
		System.out.println();
		System.out.println(employeesResponse.getBody());

		System.out.println("============= POST " + baseUrl + "/employees");

		HttpEntity<String> newEmployee = new HttpEntity<>("{\"firstName\": \"Saruman\", " + "\"lastName\": \"the White\", "
				+ "\"title\": \"Wizard\"}", headers);

		try {
			rest.exchange(baseUrl + "/employees", HttpMethod.POST, newEmployee, JsonNode.class);
			fail("Expected a security error");
		} catch (HttpClientErrorException e) {
			assertThat(e.getStatusCode(), equalTo(HttpStatus.FORBIDDEN));
		}
	}

	@Test
	public void testUrlSecurityForAdmins() {

		System.out.println("============= GET " + baseUrl + "/employees");

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/hal+json");

		String userCreds = new String(Base64.encode(("ollie:gierke").getBytes()));
		headers.set("Authorization", "Basic " + userCreds);
		headers.set("Accept", "application/hal+json");

		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(headers);

		RestTemplate rest = new RestTemplate();

		ResponseEntity<JsonNode> employeesResponse = rest.exchange(baseUrl + "/employees", HttpMethod.GET, request,
				JsonNode.class);
		employeesResponse.getHeaders().entrySet().stream()//
				.map(e -> e.getKey() + ": " + e.getValue())//
				.forEach(header -> System.out.println(header));
		assertThat(employeesResponse.getHeaders().get("Content-Type"), hasItems("application/hal+json"));
		assertThat(employeesResponse.getStatusCode(), equalTo(HttpStatus.OK));
		System.out.println();
		System.out.println(employeesResponse.getBody());

		System.out.println("============= POST " + baseUrl + "/employees");

		headers.add("Content-Type", "application/json");

		HttpEntity<String> newEmployee = new HttpEntity<>("{\"firstName\": \"Saruman\", " + "\"lastName\": \"the White\", "
				+ "\"title\": \"Wizard\"}", headers);

		ResponseEntity<JsonNode> response = rest.exchange(baseUrl + "/employees", HttpMethod.POST, newEmployee,
				JsonNode.class);
		assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
		String location = response.getHeaders().get("Location").get(0);
		Employee employee = rest.getForObject(location, Employee.class);
		assertThat(employee.getFirstName(), equalTo("Saruman"));
		assertThat(employee.getLastName(), equalTo("the White"));
		assertThat(employee.getTitle(), equalTo("Wizard"));
		System.out.println(rest.getForObject(location, String.class));
	}
}
