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
package example.springdata.mongodb.reactive;

import static org.assertj.core.api.Assertions.*;

import example.springdata.mongodb.Customer;
import example.springdata.mongodb.QCustomer;
import reactor.test.StepVerifier;

import java.util.Arrays;

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
public class ReactiveCustomerRepositoryTests {

	@Autowired ReactiveCustomerQuerydslRepository repository;
	@Autowired MongoOperations operations;

	Customer dave, oliver, carter;

	@Before
	public void setUp() {

		repository.deleteAll().as(StepVerifier::create).verifyComplete();

		dave = new Customer("Dave", "Matthews");
		oliver = new Customer("Oliver August", "Matthews");
		carter = new Customer("Carter", "Beauford");

		repository.saveAll(Arrays.asList(dave, oliver, carter)).then().as(StepVerifier::create).verifyComplete();
	}

	@Test
	public void findAllByPredicate() {

		repository.findAll(QCustomer.customer.lastname.eq("Matthews")) //
				.collectList() //
				.as(StepVerifier::create) //
				.assertNext(it -> assertThat(it).containsExactlyInAnyOrder(dave, oliver)) //
				.verifyComplete();
	}

}
