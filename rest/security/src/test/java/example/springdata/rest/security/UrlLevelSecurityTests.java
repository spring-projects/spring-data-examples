/*
 * Copyright 2014-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example.springdata.rest.security;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.util.Base64;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test cases that verify the URL level of security by using the Spring MVC test framework.
 *
 * @author Greg Turnquist
 * @author Oliver Gierke
 * @author Divya Srivastava
 */
@SpringBootTest
class UrlLevelSecurityTests {

	private static final String PAYLOAD = "{\"firstName\": \"Saruman\", \"lastName\": \"the White\", "
			+ "\"title\": \"Wizard\"}";

	@Autowired WebApplicationContext context;
	@Autowired FilterChainProxy filterChain;

	private MockMvc mvc;

	@BeforeEach
	void setUp() {

		this.mvc = webAppContextSetup(context).addFilters(filterChain).build();

		SecurityContextHolder.clearContext();
	}

	@Test
	void allowsAccessToRootResource() throws Exception {

		mvc.perform(get("/").//
				accept(MediaTypes.HAL_JSON)).//
				andExpect(content().contentTypeCompatibleWith(MediaTypes.HAL_JSON)).//
				andExpect(status().isOk());
	}

	@Test
	void rejectsPostAccessToCollectionResource() throws Exception {

		mvc.perform(post("/employees").//
				content(PAYLOAD).//
				accept(MediaTypes.HAL_JSON)).//
				andExpect(status().isUnauthorized());
	}

	@Test
	void allowsGetRequestsButRejectsPostForUser() throws Exception {

		var headers = new HttpHeaders();
		headers.add(HttpHeaders.ACCEPT, MediaTypes.HAL_JSON_VALUE);
		headers.add(HttpHeaders.AUTHORIZATION,
				"Basic " + new String(Base64.getEncoder().encodeToString(("greg:turnquist").getBytes())));

		mvc.perform(get("/employees").//
				headers(headers)).//
				andExpect(content().contentTypeCompatibleWith(MediaTypes.HAL_JSON)).//
				andExpect(status().isOk());

		mvc.perform(post("/employees").//
				headers(headers)).//
				andExpect(status().isForbidden());
	}

	@Test
	void allowsPostRequestForAdmin() throws Exception {

		var headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaTypes.HAL_JSON_VALUE);
		headers.set(HttpHeaders.AUTHORIZATION,
				"Basic " + new String(Base64.getEncoder().encodeToString(("ollie:gierke").getBytes())));

		mvc.perform(get("/employees").//
				headers(headers)).//
				andExpect(content().contentTypeCompatibleWith(MediaTypes.HAL_JSON)).//
				andExpect(status().isOk());

		headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

		var location = mvc.perform(post("/employees").//
				content(PAYLOAD).//
				headers(headers)).//
				andExpect(status().isCreated()).//
				andReturn().getResponse().getHeader(HttpHeaders.LOCATION);

		var mapper = new ObjectMapper();

		var content = mvc.perform(get(location)).//
				andReturn().getResponse().getContentAsString();
		var employee = mapper.readValue(content, Employee.class);

		assertThat(employee.getFirstName()).isEqualTo("Saruman");
		assertThat(employee.getLastName()).isEqualTo("the White");
		assertThat(employee.getTitle()).isEqualTo("Wizard");
	}
}
