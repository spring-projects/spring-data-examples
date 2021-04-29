/*
 * Copyright 2017-2021 the original author or authors.
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
package example.springdata.rest.headers;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.net.URI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Integration test for Cross-origin resource sharing.
 *
 * @author Mark Paluch
 */
@SpringBootTest
public class CrossOriginIntegrationTests {

	@Autowired WebApplicationContext context;
	@Autowired CustomerRepository customers;

	private MockMvc mvc;

	@BeforeEach
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void executePreflightRequest() throws Exception {

		var origin = "http://localhost:1234";
		var uri = URI.create("/customers");

		mvc.perform(options(uri).header(ORIGIN, origin).header(ACCESS_CONTROL_REQUEST_METHOD, "POST")) //
				.andExpect(header().string(ACCESS_CONTROL_ALLOW_ORIGIN, is(origin))) //
				.andExpect(header().string(ACCESS_CONTROL_ALLOW_METHODS, containsString("GET"))) //
				.andExpect(header().string(ACCESS_CONTROL_ALLOW_METHODS, containsString("POST"))); //
	}

	@Test
	public void executeCrossOriginRequest() throws Exception {

		var origin = "http://localhost:1234";
		var uri = URI.create("/customers");

		mvc.perform(get(uri).header(ORIGIN, origin)) //
				.andExpect(status().isOk()) //
				.andExpect(header().string(ACCESS_CONTROL_ALLOW_ORIGIN, is(origin)));
	}

	@Test
	public void rejectCrossOriginRequest() throws Exception {

		var origin = "http://foo.bar";
		var uri = URI.create("/customers");

		mvc.perform(get(uri).header(ORIGIN, origin)) //
				.andExpect(status().isForbidden());
	}
}
