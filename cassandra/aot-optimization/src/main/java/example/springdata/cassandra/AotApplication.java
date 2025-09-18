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
package example.springdata.cassandra;

import java.util.Collections;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.domain.Limit;

/**
 * Basic Spring Boot application.
 *
 * @author Mark Paluch
 */
@SpringBootApplication
public class AotApplication {

	public static void main(String[] args) {
		SpringApplication.run(AotApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(UserRepository repository, CassandraOperations operations)
			throws InterruptedException {

		operations.getCqlOperations().execute("CREATE INDEX IF NOT EXISTS user_username ON users (uname);");
		operations.getCqlOperations().execute(
				"CREATE CUSTOM INDEX IF NOT EXISTS users_lname_idx_1 ON users (lname) USING 'org.apache.cassandra.index.sasi.SASIIndex';");

		/*
		  Cassandra secondary indexes are created in the background without the possibility to check
		  whether they are available or not. So we are forced to just wait. *sigh*
		 */
		Thread.sleep(1000);

		return args -> {

			User user = new User();
			user.setId(42L);
			user.setUsername("heisenberg");
			user.setFirstname("Walter");
			user.setLastname("White");

			user.setCurrent(new Address("308 Negra Arroyo Lane", "87104", "Albuquerque"));
			user.setPrevious(Collections.singletonList(new Address("12000 – 12100 Coors Rd SW", "87045", "Albuquerque")));

			repository.save(user);

			System.out.println("------- Annotated Single -------");
			System.out.println(repository.findUserByIdIn(1000));
			System.out.println(repository.findUserByIdIn(42));

			System.out.println("------- Derived Single -------");
			System.out.println(repository.findUserByUsername(user.getUsername()));

			System.out.println("------- Derived SASI -------");
			System.out.println(repository.findUsersByLastnameStartsWith("White", Limit.of(1)));
		};

	}

}
