/*
 * Copyright 2017-2018 the original author or authors.
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
package example.springdata.jdbc.basics.simpleentity;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.jdbc.repository.config.JdbcConfiguration;
import org.springframework.data.relational.core.mapping.event.BeforeSaveEvent;
import org.springframework.data.relational.core.mapping.event.RelationalEvent;

/**
 * Contains infrastructure necessary for creating repositories and two listeners.
 * <p>
 * Not that a listener may change an entity without any problem.
 *
 * @author Jens Schauder
 * @author Mark Paluch
 */
@Configuration
@EnableJdbcRepositories
@Import(JdbcConfiguration.class)
public class CategoryConfiguration {

	@Bean
	public ApplicationListener<?> loggingListener() {

		return (ApplicationListener<ApplicationEvent>) event -> {
			if (event instanceof RelationalEvent) {
				System.out.println("Received an event: " + event);
			}
		};
	}

	@Bean
	public ApplicationListener<BeforeSaveEvent> timeStampingSaveTime() {

		return event -> {

			Object entity = event.getEntity();

			if (entity instanceof Category) {
				Category category = (Category) entity;
				category.timeStamp();
			}
		};
	}
}
