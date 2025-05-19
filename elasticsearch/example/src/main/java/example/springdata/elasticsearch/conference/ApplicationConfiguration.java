/*
 * Copyright 2020-2025 the original author or authors.
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
package example.springdata.elasticsearch.conference;

import java.time.LocalDate;
import java.util.Arrays;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

/**
 * @author Artur Konczak
 * @author Oliver Gierke
 * @author Christoph Strobl
 * @author Prakhar Gupta
 * @author Haibo Liu
 */
@SpringBootApplication
class ApplicationConfiguration {

    @Autowired ElasticsearchOperations operations;
	@Autowired ConferenceRepository repository;

	@PreDestroy
	public void deleteIndex() {
		operations.indexOps(Conference.class).delete();
	}

	@PostConstruct
	public void insertDataSample() {

		operations.indexOps(Conference.class).refresh();

		// Save data sample

		var documents = Arrays.asList(
				Conference.builder().date(LocalDate.of(2014, 11, 6)).name("Spring eXchange 2014 - London")
						.keywords(Arrays.asList("java", "spring")).location(new GeoPoint(51.500152D, -0.126236D)).build(), //
				Conference.builder().date(LocalDate.of(2014, 12, 7)).name("Scala eXchange 2014 - London")
						.keywords(Arrays.asList("scala", "play", "java")).location(new GeoPoint(51.500152D, -0.126236D)).build(), //
				Conference.builder().date(LocalDate.of(2014, 11, 20)).name("Elasticsearch 2014 - Berlin")
						.keywords(Arrays.asList("java", "elasticsearch", "kibana")).location(new GeoPoint(52.5234051D, 13.4113999))
						.build(), //
				Conference.builder().date(LocalDate.of(2014, 11, 12)).name("AWS London 2014").keywords(Arrays.asList("cloud", "aws"))
						.location(new GeoPoint(51.500152D, -0.126236D)).build(), //
				Conference.builder().date(LocalDate.of(2014, 10, 4)).name("JDD14 - Cracow").keywords(Arrays.asList("java", "spring"))
						.location(new GeoPoint(50.0646501D, 19.9449799)).build());

		repository.saveAll(documents);
	}
}
