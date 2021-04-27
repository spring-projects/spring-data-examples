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
package example.users;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.web.JsonPath;
import org.springframework.data.web.ProjectedPayload;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.xmlbeam.annotation.XBRead;

/**
 * Controller to handle web requests for {@link UserPayload}s.
 *
 * @author Oliver Gierke
 */
@RestController
class UserController {

	private static String XML_PAYLOAD = "<firstname>Dave</firstname><lastname>Matthews</lastname>";

	/**
	 * Receiving POST requests supporting both JSON and XML.
	 *
	 * @param user
	 * @return
	 */
	@PostMapping(value = "/")
	HttpEntity<String> post(@RequestBody UserPayload user) {

		return ResponseEntity
				.ok(String.format("Received firstname: %s, lastname: %s", user.getFirstname(), user.getLastname()));
	}

	/**
	 * Returns a simple JSON payload.
	 *
	 * @return
	 */
	@GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	Map<String, Object> getJson() {

		Map<String, Object> result = new HashMap<>();
		result.put("firstname", "Dave");
		result.put("lastname", "Matthews");

		return result;
	}

	/**
	 * Returns the payload of {@link #getJson()} wrapped into another element to simulate a change in the representation.
	 *
	 * @return
	 */
	@GetMapping(path = "/changed", produces = MediaType.APPLICATION_JSON_VALUE)
	Map<String, Object> getChangedJson() {
		return Collections.singletonMap("user", getJson());
	}

	/**
	 * Returns a simple XML payload.
	 *
	 * @return
	 */
	@GetMapping(path = "/", produces = MediaType.APPLICATION_XML_VALUE)
	String getXml() {
		return "<user>".concat(XML_PAYLOAD).concat("</user>");
	}

	/**
	 * Returns the payload of {@link #getXml()} wrapped into another XML element to simulate a change in the
	 * representation structure.
	 *
	 * @return
	 */
	@GetMapping(path = "/changed", produces = MediaType.APPLICATION_XML_VALUE)
	String getChangedXml() {
		return "<user><username>".concat(XML_PAYLOAD).concat("</username></user>");
	}

	/**
	 * The projection interface using XPath and JSON Path expression to selectively pick elements from the payload.
	 *
	 * @author Oliver Gierke
	 */
	@ProjectedPayload
	public interface UserPayload {

		@XBRead("//firstname")
		@JsonPath("$..firstname")
		String getFirstname();

		@XBRead("//lastname")
		@JsonPath("$..lastname")
		String getLastname();
	}
}
