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
package example.users.web;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import example.users.User;
import example.users.UserManagement;

/**
 * A sample controller implementation to showcase Spring Data web support:
 * <ol>
 * <li>Automatic population of Specification objects.</li>
 * </ol>
 * 
 * TODO Don't think that a RestController is necessary, but i didn't want to mess around with the original controller.
 * TODO Add the actual specification parameter to the list of parametersof {@link #users}.
 * 
 * @author Michael J. Simons
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/users")
public class UserRestController {
	
	private final UserManagement userManagement;

	/**
	 * Returns a {@link Page} of {@link User}s. Spring Data automatically populates the {@link Pageable} from
	 * request data according to the setup of {@link PageableHandlerMethodArgumentResolver}. Note how the defaults can be
	 * tweaked by using {@link PageableDefault}.
	 * 
	 * @param pageable will never be {@literal null}.
	 * @return
	 */	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<User> users(@PageableDefault(size = 5) Pageable pageable) {
		return userManagement.findAll(pageable);
	}
}
