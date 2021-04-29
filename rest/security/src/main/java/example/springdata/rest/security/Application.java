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

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * This example shows various ways to secure Spring Data REST applications using Spring Security
 *
 * @author Greg Turnquist
 */
@SpringBootApplication
public class Application {

	@Autowired ItemRepository itemRepository;
	@Autowired EmployeeRepository employeeRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	/**
	 * Pre-load the system with employees and items.
	 */
	public @PostConstruct void init() {

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

	/**
	 * This application is secured at both the URL level for some parts, and the method level for other parts. The URL
	 * security is shown inside this code, while method-level annotations are enabled at by
	 * {@link EnableGlobalMethodSecurity}.
	 *
	 * @author Greg Turnquist
	 * @author Oliver Gierke
	 */
	@Configuration
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	@EnableWebSecurity
	static class SecurityConfiguration extends WebSecurityConfigurerAdapter {

		/**
		 * This section defines the user accounts which can be used for authentication as well as the roles each user has.
		 */
		@Bean
		InMemoryUserDetailsManager userDetailsManager() {

			var builder = User.builder().passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder()::encode);

			var greg = builder.username("greg").password("turnquist").roles("USER").build();
			var ollie = builder.username("ollie").password("gierke").roles("USER", "ADMIN").build();

			return new InMemoryUserDetailsManager(greg, ollie);
		}

		/**
		 * This section defines the security policy for the app.
		 * <p>
		 * <ul>
		 * <li>BASIC authentication is supported (enough for this REST-based demo).</li>
		 * <li>/employees is secured using URL security shown below.</li>
		 * <li>CSRF headers are disabled since we are only testing the REST interface, not a web one.</li>
		 * </ul>
		 * NOTE: GET is not shown which defaults to permitted.
		 *
		 * @param http
		 * @throws Exception
		 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
		 */
		@Override
		protected void configure(HttpSecurity http) throws Exception {

			http.httpBasic().and().authorizeRequests().//
					antMatchers(HttpMethod.POST, "/employees").hasRole("ADMIN").//
					antMatchers(HttpMethod.PUT, "/employees/**").hasRole("ADMIN").//
					antMatchers(HttpMethod.PATCH, "/employees/**").hasRole("ADMIN").and().//
					csrf().disable();
		}
	}
}
