/*
 * Copyright 2014-2015 the original author or authors.
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
package example.springdata.rest.stores;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

/**
 * Spring configuration class main application bootstrap point.
 * 
 * @author Oliver Gierke
 */
@SpringBootApplication
public class StoreApp {

	public static void main(String[] args) {
		SpringApplication.run(StoreApp.class, args);
	}

	// Workaround for https://github.com/spring-projects/spring-boot/issues/4336
	public @Bean Module namesModule() {
		return new ParameterNamesModule();
	}
}
