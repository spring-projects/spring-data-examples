/*
 * Copyright 2019 the original author or authors.
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
package example.springdata.elasticsearch.conference;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;

import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient;
import org.springframework.data.elasticsearch.client.reactive.ReactiveRestClients;
import org.springframework.data.elasticsearch.config.AbstractReactiveElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchEntityMapper;
import org.springframework.data.elasticsearch.core.EntityMapper;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.repository.config.EnableReactiveElasticsearchRepositories;
import reactor.test.StepVerifier;

/**
 * @author Christoph Strobl
 */
@Configuration
@EnableReactiveElasticsearchRepositories
class ApplicationConfiguration extends AbstractReactiveElasticsearchConfiguration {

	@Autowired ReactiveElasticsearchOperations operations;
	@Autowired ConferenceRepository repository;

	@Bean
	@Override
	public ReactiveElasticsearchClient reactiveElasticsearchClient() {
		return ReactiveRestClients.create(ClientConfiguration.localhost());
	}

	@Override
	public EntityMapper entityMapper() {
		return new ElasticsearchEntityMapper(elasticsearchMappingContext(),
				new DefaultConversionService());
	}

	@PostConstruct
	public void insertDataSample() {

		try {
			RestClients.create(ClientConfiguration.localhost()).rest().indices().create(new CreateIndexRequest("conference-index"), RequestOptions.DEFAULT);
		} catch (IOException | ElasticsearchStatusException e) {
			// just ignore it
		}

		// Remove all documents
		repository.deleteAll().subscribe();

		// Save data sample
		repository.save(Conference.builder().date("2014-11-06").name("Spring eXchange 2014 - London")
				.keywords(Arrays.asList("java", "spring")).location(new GeoPoint(51.500152D, -0.126236D)).build()).then().as(StepVerifier::create).verifyComplete();
		repository.save(Conference.builder().date("2014-12-07").name("Scala eXchange 2014 - London")
				.keywords(Arrays.asList("scala", "play", "java")).location(new GeoPoint(51.500152D, -0.126236D)).build()).then().as(StepVerifier::create).verifyComplete();
		repository.save(Conference.builder().date("2014-11-20").name("Elasticsearch 2014 - Berlin")
				.keywords(Arrays.asList("java", "elasticsearch", "kibana")).location(new GeoPoint(52.5234051D, 13.4113999))
				.build()).then().as(StepVerifier::create).verifyComplete();
		repository.save(Conference.builder().date("2014-11-12").name("AWS London 2014")
				.keywords(Arrays.asList("cloud", "aws")).location(new GeoPoint(51.500152D, -0.126236D)).build()).then().as(StepVerifier::create).verifyComplete();
		repository.save(Conference.builder().date("2014-10-04").name("JDD14 - Cracow")
				.keywords(Arrays.asList("java", "spring")).location(new GeoPoint(50.0646501D, 19.9449799)).build()).then().as(StepVerifier::create).verifyComplete();
	}
}
