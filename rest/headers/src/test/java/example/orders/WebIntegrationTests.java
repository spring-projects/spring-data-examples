/*
 * Copyright 2015 the original author or authors.
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
package example.orders;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.restdocs.RestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.net.URI;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.restdocs.config.RestDocumentationConfigurer;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UriTemplate;

/**
 * @author Oliver Gierke
 * @soundtrack The Intersphere - Out of phase (Live at Alte Feuerwache Mannheim)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
public class WebIntegrationTests {

	@Autowired WebApplicationContext context;
	@Autowired CustomerRepository customers;

	MockMvc mvc;

	@Before
	public void setUp() {

		this.mvc = MockMvcBuilders.webAppContextSetup(context).//
				apply(new RestDocumentationConfigurer()).//
				build();
	}

	@Test
	@Ignore // see https://jira.spring.io/browse/SPR-13090
	public void executeConditionalGetRequests() throws Exception {

		Customer customer = customers.findAll().iterator().next();
		URI uri = new UriTemplate("/customers/{id}").expand(customer.getId());

		MockHttpServletResponse response = mvc.perform(get(uri)).//
				andDo(print()).//
				andReturn().getResponse();

		// ETag-based

		mvc.perform(get(uri).header(IF_NONE_MATCH, response.getHeader(ETAG))).//
				andExpect(status().isNotModified()).//
				andDo(document("if-none-match"));

		// Last-modified-based

		mvc.perform(get(uri).header(IF_MODIFIED_SINCE, response.getHeader(LAST_MODIFIED))).//
				andExpect(status().isNotModified()).//
				andDo(document("if-modified-since"));
	}
}
