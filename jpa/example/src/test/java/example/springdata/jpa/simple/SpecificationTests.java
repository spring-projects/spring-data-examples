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
package example.springdata.jpa.simple;

import static org.assertj.core.api.Assertions.*;

import jakarta.persistence.EntityManager;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.PredicateSpecification;
import org.springframework.data.jpa.domain.UpdateSpecification;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for {@link SimpleUserRepository} to show specification capabilities.
 *
 * @author Mark Paluch
 */
@SpringBootTest
@Transactional
class SpecificationTests {

	@Autowired SimpleUserRepository repository;

	@Autowired EntityManager entityManager;

	@BeforeEach
	void setUp() {

		repository.save(new User("Dave", "Matthews"));
		repository.save(new User("Carter", "Beauford"));
	}

	@Test
	void selectWithSpecification() {

		PredicateSpecification<User> isFirstName = (from, cb) -> cb.equal(from.get("firstname"), "Dave");
		PredicateSpecification<User> isLastName = (from, cb) -> cb.equal(from.get("lastname"), "Matthews");

		List<User> result = repository.findAll(isFirstName.and(isLastName));

		assertThat(result).hasSize(1);
		assertThat(result).extracting(User::getFirstname).containsOnly("Dave");
	}

	@Test
	void updateWithSpecification() {

		PredicateSpecification<User> isFirstName = (from, cb) -> cb.equal(from.get("firstname"), "Dave");
		PredicateSpecification<User> isLastName = (from, cb) -> cb.equal(from.get("lastname"), "Matthews");

		UpdateSpecification<User> specification = UpdateSpecification.<User> update((root, update, criteriaBuilder) -> {
			update.set("firstname", "Dan");
			update.set("lastname", "Brown");
		}).where(isFirstName.and(isLastName));

		assertThat(repository.update(specification)).isEqualTo(1);
		entityManager.clear();

		assertThat(repository.findAll(isFirstName)).isEmpty();

		List<User> result = repository.findAll((from, cb) -> cb.equal(from.get("firstname"), "Dan"));

		assertThat(result).hasSize(1);
		assertThat(result).extracting(User::getFirstname).containsOnly("Dan");
		assertThat(result).extracting(User::getLastname).containsOnly("Brown");
	}

}
