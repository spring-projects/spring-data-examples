/*
 * Copyright 2015-2021 the original author or authors.
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
import static org.springframework.restdocs.RestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.config.RestDocumentationConfigurer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UriTemplate;

/**
 * @author Oliver Gierke
 * @soundtrack The Intersphere - Out of phase (Live at Alte Feuerwache Mannheim)
 */
@SpringBootTest
public class WebIntegrationTests {

	@Autowired WebApplicationContext context;
	@Autowired CustomerRepository customers;

	private MockMvc mvc;

	@BeforeEach
	public void setUp() {

		this.mvc = MockMvcBuilders.webAppContextSetup(context).//
				apply(new RestDocumentationConfigurer()).//
				build();
	}

	@Test
	public void executeConditionalGetRequests() throws Exception {

		var customer = customers.findAll().iterator().next();
		var uri = new UriTemplate("/customers/{id}").expand(customer.getId());

		var response = mvc.perform(get(uri)).//
				andExpect(header().string(ETAG, is(notNullValue()))).//
				andExpect(header().string(LAST_MODIFIED, is(notNullValue()))).//
				andReturn().getResponse();

		// ETag-based

		response = mvc.perform(get(uri).header(IF_NONE_MATCH, response.getHeader(ETAG))).//
				andExpect(status().isNotModified()).//
				andExpect(header().string(ETAG, is(notNullValue()))).//
				andExpect(header().string(LAST_MODIFIED, is(notNullValue()))).//
				andDo(document("if-none-match")).//
				andReturn().getResponse();

		// Last-modified-based

		mvc.perform(get(uri).header(IF_MODIFIED_SINCE, response.getHeader(LAST_MODIFIED))).//
				andExpect(status().isNotModified()).//
				andExpect(header().string(ETAG, is(notNullValue()))).//
				andExpect(header().string(LAST_MODIFIED, is(notNullValue()))).//
				andDo(document("if-modified-since"));
	}
}
