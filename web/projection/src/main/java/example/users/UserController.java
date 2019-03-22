/*
 * Copyright 2015-2016 the original author or authors.
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

import org.springframework.data.web.JsonPath;
import org.springframework.data.web.ProjectedPayload;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xmlbeam.annotation.XBRead;

/**
 * Controller to handle web requests for {@link UserPayload}s.
 * 
 * @author Oliver Gierke
 */
@RestController
class UserController {

	@RequestMapping(value = "/", method = RequestMethod.POST)
	HttpEntity<String> index(@RequestBody UserPayload user) {

		return ResponseEntity
				.ok(String.format("Received firstname: %s, lastname: %s", user.getFirstname(), user.getLastname()));
	}

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
