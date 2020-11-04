/*
 * Copyright 2020 the original author or authors.
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
package example.springdata.mongodb;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Integration tests showing {@link org.springframework.data.repository.core.support.RepositoryMethodInvocationListener}
 * using query method in action.
 *
 * @author Christoph Strobl
 */
@SpringBootTest
class DemoApplicationTests {

	@Autowired PersonRepository repo;

	@Test
	void repositoryMetrics() throws InterruptedException {

		Person fiona = new Person();
		fiona.id = "p-1";
		fiona.name = "fiona";

		Person frank = new Person();
		frank.id = "p-2";
		frank.name = "frank";

		System.out.println("- Execute - Save Fiona -");
		repo.save(fiona).then().as(StepVerifier::create).verifyComplete();

		System.out.println("- Prepare - Find All -"); // Nothing captured - Invocation will happen in line 53 when subscribing
		Flux<Person> all = repo.findAll();

		System.out.println("- Execute - Save Frank -");
		repo.save(frank).as(StepVerifier::create).expectNextCount(1).verifyComplete();

		System.out.println("- Invoke Prepared - Find All -");
		all.as(StepVerifier::create).expectNextCount(2).verifyComplete(); // subscription triggers invocation/metrics collection.

		System.out.println("- Pause - Main Thread");
		Thread.sleep(1000);

		System.out.println("- Invoke Prepared - Find All -");
		all.as(StepVerifier::create).expectNextCount(2).verifyComplete();

		System.out.println("- Delay - After Invocation -");
		all.delayElements(Duration.ofMillis(1000)).as(StepVerifier::create).expectNextCount(2).verifyComplete();

		System.out.println("- Execute - Find By Name -");
		repo.findByName("fiona").as(StepVerifier::create).expectNextCount(1).verifyComplete();

		System.out.println("- Take - One of Many -");
		repo.findAll().take(1).as(StepVerifier::create).expectNextCount(1).verifyComplete();

		System.out.println("- Execute - Invalid Query -");
		repo.findError().as(StepVerifier::create).verifyError();
	}
}
