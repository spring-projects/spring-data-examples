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
package example.users;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Christoph Strobl
 * @author Oliver Gierke
 */
@SpringBootApplication
public class Application extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		// Configure resource handler explicitly to enable non-versioned
		// Webjars in Thymeleaf templates
		registry.addResourceHandler("/webjars/**").//
				addResourceLocations("classpath:/META-INF/resources/webjars/").//
				resourceChain(true);
	}

	@Autowired UserRepository repo;

	@PostConstruct
	void initialize() throws Exception {

		// Import demo users from local CSV
		new UserInitializer(repo).initLocally();

		// Import demo users from remote service
		// new UserInitializer(repo).initRemote(100);
	}
}
