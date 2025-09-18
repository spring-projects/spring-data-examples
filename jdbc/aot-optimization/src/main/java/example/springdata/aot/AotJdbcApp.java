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
package example.springdata.aot;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jdbc.core.dialect.JdbcH2Dialect;

/**
 * @author Mark Paluch
 */
@SpringBootApplication
public class AotJdbcApp {

	public static void main(String[] args) {
		SpringApplication.run(AotJdbcApp.class, args);
	}

	@Bean
	JdbcH2Dialect dialect() {
		return JdbcH2Dialect.INSTANCE;
	}

	@Bean
	CommandLineRunner commandLineRunner(CategoryRepository repository) {

		return args -> {

			System.out.println("--------------------------------------");
			System.out.println("-- findAllByNameContaining(\"dings\") --");
			System.out.println("--------------------------------------");
			List<Category> categories = repository.findAllByNameContaining("dings");
			categories.forEach(it -> System.out
					.println("Id: %s, Name: %s, Description: %s".formatted(it.getId(), it.getName(), it.getDescription())));

			System.out.println();
			System.out.println();

			System.out.println("--------------------------------------------");
			System.out.println("-- findProjectedByNameContaining(\"dings\") --");
			System.out.println("--------------------------------------------");
			List<CategoryProjection> dings = repository.findProjectedByNameContaining("dings");
			dings.forEach(it -> System.out.println("Name: %s, Description: %s, Combined: %s".formatted(it.getName(),
					it.getDescription(), it.getNameAndDescription())));
		};
	}
}
