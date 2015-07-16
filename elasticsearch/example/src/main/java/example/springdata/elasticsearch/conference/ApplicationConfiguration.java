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
package example.springdata.elasticsearch.conference;

import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

/**
 * @author Artur Konczak
 * @author Oliver Gierke
 */
@Configuration
@EnableAutoConfiguration
class ApplicationConfiguration {

	@Autowired ElasticsearchOperations operations;
	@Autowired ConferenceRepository repository;

	@PreDestroy
	public void deleteIndex() {
		operations.deleteIndex(Conference.class);
	}

	@PostConstruct
	public void insertDataSample() {

		// Remove all documents
		repository.deleteAll();
		operations.refresh(Conference.class, true);

		// Save data sample
		repository.save(Conference.builder().date("2014-11-06").name("Spring eXchange 2014 - London")
				.keywords(Arrays.asList("java", "spring")).location("51.500152, -0.126236").build());
		repository.save(Conference.builder().date("2014-12-07").name("Scala eXchange 2014 - London")
				.keywords(Arrays.asList("scala", "play", "java")).location("51.500152, -0.126236").build());
		repository.save(Conference.builder().date("2014-11-20").name("Elasticsearch 2014 - Berlin")
				.keywords(Arrays.asList("java", "elasticsearch", "kibana")).location("52.5234051, 13.4113999").build());
		repository.save(Conference.builder().date("2014-11-12").name("AWS London 2014")
				.keywords(Arrays.asList("cloud", "aws")).location("51.500152, -0.126236").build());
		repository.save(Conference.builder().date("2014-10-04").name("JDD14 - Cracow")
				.keywords(Arrays.asList("java", "spring")).location("50.0646501, 19.9449799").build());
	}
}
