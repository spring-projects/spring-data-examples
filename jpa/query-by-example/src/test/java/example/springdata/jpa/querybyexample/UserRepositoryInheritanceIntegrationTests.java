/*
 * Copyright 2016-2021 the original author or authors.
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
package example.springdata.jpa.querybyexample;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration test showing the usage of JPA Query-by-Example support through Spring Data repositories and entities
 * using inheritance.
 *
 * @author Mark Paluch
 * @author Oliver Gierke
 * @author Divya Srivastava
 * @author Jens Schauder
 */

@Transactional
@SpringBootTest
public class UserRepositoryInheritanceIntegrationTests {

	@Autowired UserRepository repository;

	User skyler, walter, flynn;

	@BeforeEach
	public void setUp() {

		repository.deleteAll();

		this.skyler = repository.save(new User("Skyler", "White", 45));
		this.walter = repository.save(new SpecialUser("Walter", "White", 50));
		this.flynn = repository.save(new SpecialUser("Walter Jr. (Flynn)", "White", 17));
	}

	/**
	 * @see #153
	 */
	@Test
	public void countByExample() {
		assertThat(repository.count(Example.of(new User(null, "White", null)))).isEqualTo(3L);
	}

	/**
	 * @see #153
	 */
	@Test
	public void countSubtypesByExample() {
		assertThat(repository.count(Example.of(new SpecialUser(null, "White", null)))).isEqualTo(2L);
	}
}
