/*
 * Copyright 2019-2021 the original author or authors.
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
package example.springdata.mongodb.imperative;

import static org.assertj.core.api.Assertions.*;

import example.springdata.mongodb.Customer;
import example.springdata.mongodb.QCustomer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoOperations;

/**
 * @author Christoph Strobl
 */
@DataMongoTest
class CustomerRepositoryTests {

	@Autowired CustomerQuerydslRepository repository;
	@Autowired MongoOperations operations;

	private Customer dave, oliver, carter;

	@BeforeEach
	void setUp() {

		repository.deleteAll();

		dave = repository.save(new Customer("Dave", "Matthews"));
		oliver = repository.save(new Customer("Oliver August", "Matthews"));
		carter = repository.save(new Customer("Carter", "Beauford"));
	}

	@Test
	void findAllByPredicate() {
		assertThat(repository.findAll(QCustomer.customer.lastname.eq("Matthews"))).containsExactlyInAnyOrder(dave, oliver);
	}

}
