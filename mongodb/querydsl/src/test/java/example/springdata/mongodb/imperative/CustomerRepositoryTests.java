/*
 * Copyright 2019 the original author or authors.
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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Christoph Strobl
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerRepositoryTests {

	@Autowired CustomerQuerydslRepository repository;
	@Autowired MongoOperations operations;

	Customer dave, oliver, carter;

	@Before
	public void setUp() {

		repository.deleteAll();

		dave = repository.save(new Customer("Dave", "Matthews"));
		oliver = repository.save(new Customer("Oliver August", "Matthews"));
		carter = repository.save(new Customer("Carter", "Beauford"));
	}

	@Test
	public void findAllByPredicate() {
		assertThat(repository.findAll(QCustomer.customer.lastname.eq("Matthews"))).containsExactlyInAnyOrder(dave, oliver);
	}

}
