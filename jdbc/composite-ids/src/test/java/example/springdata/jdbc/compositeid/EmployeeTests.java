/*
 * Copyright 2025 the original author or authors.
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
package example.springdata.jdbc.compositeid;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jdbc.test.autoconfigure.AutoConfigureDataJdbc;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Test demonstrating the use of composite ids.
 *
 * @author Jens Schauder
 */
@SpringBootTest(classes = CompositeConfiguration.class)
@AutoConfigureDataJdbc
class EmployeeTests {

	@Autowired
	EmployeeRepository repository;

	@Test
	void employeeDirectInsert() {

		Employee employee = repository.insert(new Employee(new EmployeeId(Organization.RND, 23L), "Jens Schauder"));

		Employee reloaded = repository.findById(employee.id);

		assertThat(reloaded.name).isEqualTo(employee.name);
	}

	@Test
	void employeeIdGeneration() {

		Employee employee = repository.save(new Employee("Mark Paluch"));

		assertThat(employee.id).isNotNull();
	}
}
