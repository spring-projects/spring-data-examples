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
package example.springdata.rest.security;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * This example shows various ways to secure Spring Data REST applications using Spring Security
 *
 * @author Greg Turnquist
 */
@ComponentScan
@EnableAutoConfiguration
public class Application {

	@Autowired ItemRepository itemRepository;
	@Autowired EmployeeRepository employeeRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	/**
	 * Pre-load the system with employees and items.
	 */
	@PostConstruct
	public void init() {

		employeeRepository.save(new Employee("Bilbo", "Baggins", "thief"));
		employeeRepository.save(new Employee("Frodo", "Baggins", "ring bearer"));
		employeeRepository.save(new Employee("Gandalf", "the Wizard", "servant of the Secret Fire"));

		/**
		 * Due to method-level protections on {@link example.company.ItemRepository}, the security context must be loaded
		 * with an authentication token containing the necessary privileges.
		 */
		SecurityUtils.runAs("system", "system", "ROLE_ADMIN");

		itemRepository.save(new Item("Sting"));
		itemRepository.save(new Item("the one ring"));

		SecurityContextHolder.clearContext();
	}
}
