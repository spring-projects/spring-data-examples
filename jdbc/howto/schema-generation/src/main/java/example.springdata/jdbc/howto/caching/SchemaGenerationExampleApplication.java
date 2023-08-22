/*
 * Copyright 2023 the original author or authors.
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
package example.springdata.jdbc.howto.caching;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Boot application that we use as a fixture for demonstrating Schema Generation.
 * See SchemaGenerationTest in the test directory for the interesting stuff.
 *
 * @author Jens Schauder
 * @since 3.2
 */
@EnableCaching
@SpringBootApplication
class SchemaGenerationExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchemaGenerationExampleApplication.class, args);
	}

}
