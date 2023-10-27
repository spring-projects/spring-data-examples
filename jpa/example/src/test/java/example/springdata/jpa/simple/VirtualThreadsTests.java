/*
 * Copyright 2023 the original author or authors.
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

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.JRE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration test showing the basic usage of {@link SimpleUserRepository} with Virtual Threads.
 *
 * @author Mark Paluch
 */
@Transactional
@SpringBootTest(properties = "spring.threads.virtual.enabled=true")
@EnabledOnJre(JRE.JAVA_21)
class VirtualThreadsTests {

	@Autowired SimpleUserRepository repository;
	private User user;

	@BeforeEach
	void setUp() {

		user = new User();
		user.setUsername("foobar");
		user.setFirstname("firstname");
		user.setLastname("lastname");
	}

	/**
	 * This repository invocation runs on a dedicated virtual thread.
	 */
	@Test
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	void supportsVirtualThreads() throws Exception {

		BlockingQueue<String> thread = new LinkedBlockingQueue<>();
		repository.save(new User("Customer1", "Foo"));
		repository.save(new User("Customer2", "Bar"));

		try (SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor()) {
			executor.setVirtualThreads(true);

			var future = executor.submit(() -> {
				thread.add(Thread.currentThread().toString());
				return repository.findAll();
			});

			List<User> users = future.get();
			String threadName = thread.poll(1, TimeUnit.SECONDS);

			assertThat(threadName).contains("VirtualThread");
			assertThat(users).hasSize(2);
		}

		repository.deleteAll();
	}

	/**
	 * Here we demonstrate the usage of {@link CompletableFuture} as a result wrapper for asynchronous repository query
	 * methods running on Virtual Threads. Note, that we need to disable the surrounding transaction to be able to
	 * asynchronously read the written data from another thread within the same test method.
	 */
	@Test
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	void asyncUsesVirtualThreads() throws Exception {

		BlockingQueue<String> thread = new LinkedBlockingQueue<>();
		repository.save(new User("Customer1", "Foo"));
		repository.save(new User("Customer2", "Bar"));

		var future = repository.readAllBy().thenAccept(users -> {

			assertThat(users).hasSize(2);
			thread.add(Thread.currentThread().toString());
		});

		future.join();
		String threadName = thread.poll(1, TimeUnit.SECONDS);

		assertThat(threadName).contains("VirtualThread");

		repository.deleteAll();
	}
}
