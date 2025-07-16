/*
 * Copyright 2025 the original author or authors.
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
package example.springdata.jdbc.compositeid;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;

import java.util.concurrent.atomic.AtomicLong;


/**
 * Configuration for the demonstration of composite ids.
 *
 * Registers a {@link BeforeConvertCallback} for generating ids.
 *
 * @author Jens Schauder
 */
@Configuration
@EnableJdbcRepositories
public class CompositeConfiguration extends AbstractJdbcConfiguration {

	@Bean
	BeforeConvertCallback<Employee> idGeneration() {
		return new BeforeConvertCallback<>() {
			AtomicLong counter = new AtomicLong();

			@Override
			public Employee onBeforeConvert(Employee employee) {
				if (employee.id == null) {
					employee.id = new EmployeeId(Organization.RND, counter.addAndGet(1));
				}
				return employee;
			}
		};
	}
}
