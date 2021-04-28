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
package example.springdata.mongodb.people;

import static org.assertj.core.api.Assertions.*;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import rx.RxReactiveStreams;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * Integration test for {@link ReactiveMongoTemplate}.
 *
 * @author Mark Paluch
 */
@DataMongoTest
class ReactiveMongoTemplateIntegrationTest {

	@Autowired ReactiveMongoTemplate template;

	@BeforeEach
	void setUp() {

		StepVerifier.create(template.dropCollection(Person.class)).verifyComplete();

		var insertAll = template
				.insertAll(Flux.just(new Person("Walter", "White", 50), //
						new Person("Skyler", "White", 45), //
						new Person("Saul", "Goodman", 42), //
						new Person("Jesse", "Pinkman", 27)).collectList());

		insertAll.as(StepVerifier::create).expectNextCount(4).verifyComplete();
	}

	/**
	 * This sample performs a count, inserts data and performs a count again using reactive operator chaining. It prints
	 * the two counts ({@code 4} and {@code 6}) to the console.
	 */
	@Test
	void shouldInsertAndCountData() {

		var count = template.count(new Query(), Person.class) //
				.doOnNext(System.out::println) //
				.thenMany(template.insertAll(Arrays.asList(new Person("Hank", "Schrader", 43), //
						new Person("Mike", "Ehrmantraut", 62)))) //
				.last() //
				.flatMap(v -> template.count(new Query(), Person.class)) //
				.doOnNext(System.out::println);//

		count.as(StepVerifier::create).expectNext(6L).verifyComplete();
	}

	/**
	 * Note that the all object conversions are performed before the results are printed to the console.
	 */
	@Test
	void convertReactorTypesToRxJava2() {

		var flux = template.find(Query.query(Criteria.where("lastname").is("White")), Person.class);

		long count = RxReactiveStreams.toObservable(flux).count().toSingle().toBlocking().value();

		assertThat(count).isEqualTo(2);
	}
}
