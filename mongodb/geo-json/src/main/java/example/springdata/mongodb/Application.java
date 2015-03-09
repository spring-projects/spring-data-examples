/*
 * Copyright 2015 the original author or authors.
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

import static com.fasterxml.jackson.databind.DeserializationFeature.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.geo.GeoModule;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.repository.init.AbstractRepositoryPopulatorFactoryBean;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Christoph Strobl
 */
@SpringBootApplication
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	/**
	 * Read JSON data from disk and insert those stores.
	 * 
	 * @return
	 */
	public @Bean AbstractRepositoryPopulatorFactoryBean repositoryPopulator() {

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new GeoJsonModule());
		mapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);

		Jackson2RepositoryPopulatorFactoryBean factoryBean = new Jackson2RepositoryPopulatorFactoryBean();
		factoryBean.setResources(new Resource[] { new ClassPathResource("starbucks-in-nyc.json") });
		factoryBean.setMapper(mapper);

		return factoryBean;

	}

	/**
	 * Creates a new {@link GeoJsonModule} registering mixins for common and MongoDB geo-spatial types. <br />
	 * This should be part of Spring Data MongoDB.
	 */
	static class GeoJsonModule extends GeoModule {

		private static final long serialVersionUID = 6239912797617786302L;

		public GeoJsonModule() {
			super();

			setMixInAnnotation(GeoJsonPoint.class, GeoJsonPointMixin.class);
		}
	}

	static abstract class GeoJsonPointMixin {
		GeoJsonPointMixin(@JsonProperty("longitude") double x, @JsonProperty("latitude") double y) {}
	}
}
