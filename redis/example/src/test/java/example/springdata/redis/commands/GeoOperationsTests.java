/*
 * Copyright 2017-2021 the original author or authors.
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
package example.springdata.redis.commands;

import static org.assertj.core.api.Assertions.*;

import example.springdata.redis.test.condition.EnabledOnCommand;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands.DistanceUnit;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisOperations;

/**
 * Show usage of redis geo-index operations using Template API provided by {@link GeoOperations}.
 *
 * @author Mark Paluch
 */
@SpringBootTest
@EnabledOnCommand("GEOADD")
class GeoOperationsTests {

	@Autowired RedisOperations<String, String> operations;
	private GeoOperations<String, String> geoOperations;

	@BeforeEach
	void before() {

		geoOperations = operations.opsForGeo();

		geoOperations.geoAdd("Sicily", new Point(13.361389, 38.115556), "Arigento");
		geoOperations.geoAdd("Sicily", new Point(15.087269, 37.502669), "Catania");
		geoOperations.geoAdd("Sicily", new Point(13.583333, 37.316667), "Palermo");
	}

	/**
	 * Look up points using a geo-index member as reference.
	 */
	@Test
	void geoRadiusByMember() {

		var byDistance = geoOperations.geoRadiusByMember("Sicily", "Palermo",
				new Distance(100, DistanceUnit.KILOMETERS));

		assertThat(byDistance).hasSize(2).extracting("content.name").contains("Arigento", "Palermo");

		var greaterDistance = geoOperations.geoRadiusByMember("Sicily", "Palermo",
				new Distance(200, DistanceUnit.KILOMETERS));

		assertThat(greaterDistance).hasSize(3).extracting("content.name").contains("Arigento", "Catania", "Palermo");
	}

	/**
	 * Lookup points within a circle around coordinates.
	 */
	@Test
	void geoRadius() {

		var circle = new Circle(new Point(13.583333, 37.316667), //
				new Distance(100, DistanceUnit.KILOMETERS));
		var result = geoOperations.geoRadius("Sicily", circle);

		assertThat(result).hasSize(2).extracting("content.name").contains("Arigento", "Palermo");
	}

	/**
	 * Calculate the distance between two geo-index members.
	 */
	@Test
	void geoDistance() {

		var distance = geoOperations.geoDist("Sicily", "Catania", "Palermo", DistanceUnit.KILOMETERS);

		assertThat(distance.getValue()).isBetween(130d, 140d);
	}

	/**
	 * Return the geo-hash.
	 */
	@Test
	void geoHash() {

		var geohashes = geoOperations.geoHash("Sicily", "Catania", "Palermo");

		assertThat(geohashes).hasSize(2).contains("sqdtr74hyu0", "sq9sm1716e0");
	}
}
