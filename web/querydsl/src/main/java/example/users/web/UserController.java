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
package example.users.web;

import example.users.User;
import example.users.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.querydsl.core.types.Predicate;

/**
 * Controller to handle web requests for {@link User}s.
 *
 * @author Christoph Strobl
 * @author Oliver Gierke
 */
@Controller
@RequiredArgsConstructor
class UserController {

	private final UserRepository repository;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	String index(Model model, //
			@QuerydslPredicate(root = User.class) Predicate predicate, //
			@PageableDefault(sort = { "lastname", "firstname" }) Pageable pageable, //
			@RequestParam MultiValueMap<String, String> parameters) {

		ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequest();
		builder.replaceQueryParam("page", new Object[0]);

		model.addAttribute("baseUri", builder.build().toUri());
		model.addAttribute("users", repository.findAll(predicate, pageable));

		return "index";
	}
}
