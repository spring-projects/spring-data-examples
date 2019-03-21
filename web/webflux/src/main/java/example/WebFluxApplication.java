/*
 * Copyright 2018 the original author or authors.
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
package example;

import example.jpa.BankAccount;
import example.jpa.BankAccountRepository;
import example.mongo.Person;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.metadata.DataSourcePoolMetadataProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.support.DefaultJpaExecutor;
import org.springframework.data.jpa.support.JpaExecutor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Main application configuration.
 *
 * @author Mark Paluch
 */
@SpringBootApplication
public class WebFluxApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebFluxApplication.class, args);
	}

	/**
	 * Initialize {@link Person} collection using the synchronous API to avoid synchronization and threading issues
	 * through a {@link CommandLineRunner}.
	 */
	@Bean
	CommandLineRunner initializer(MongoOperations mongoOperations, BankAccountRepository accountRepository) {

		return args -> {

			List<Person> people = Arrays.asList(new Person("Walter", "White"), new Person("Hank", "Schrader"),
					new Person("Jesse", "Pinkman"));

			mongoOperations.dropCollection(Person.class);
			mongoOperations.insert(people,
					Person.class);

			people.stream() //
					.map(it -> new BankAccount(it.getId(), new BigDecimal(1_000_000))) //
					.forEach(accountRepository::save);
		};
	}

	/**
	 * Configure a {@link DefaultJpaExecutor} to bridge blocking JPA calls into a reactive flow.
	 */
	@Bean
	JpaExecutor jpaExecutor(EntityManagerFactory entityManagerFactory, PlatformTransactionManager transactionManager,
			@Qualifier("jpa-executor-scheduler") Scheduler jpaExecutor) {

		CustomizableThreadFactory threadFactory = new CustomizableThreadFactory("jpa-executor");
		threadFactory.setDaemon(true);

		return new DefaultJpaExecutor(entityManagerFactory, transactionManager, jpaExecutor);
	}

	/**
	 * Configure a {@link DefaultJpaExecutor} to bridge blocking JPA calls into a reactive flow. Using
	 */
	@Bean(destroyMethod = "dispose")
	@Qualifier("jpa-executor-scheduler")
	Scheduler jpaScheduler(DataSourcePoolMetadataProvider poolMetadataProvider, DataSource source) {

		Integer max = poolMetadataProvider.getDataSourcePoolMetadata(source).getMax();

		CustomizableThreadFactory threadFactory = new CustomizableThreadFactory("jpa-executor");
		threadFactory.setDaemon(true);

		return Schedulers.newParallel(max, threadFactory);
	}
}
