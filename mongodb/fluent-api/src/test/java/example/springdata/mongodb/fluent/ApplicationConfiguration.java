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
package example.springdata.mongodb.fluent;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeospatialIndex;

/**
 * @author Christoph Strobl
 */
@SpringBootApplication
public class ApplicationConfiguration {

	static final String COLLECTION = "star-wars";

	@Bean
	CommandLineRunner init(MongoTemplate template) {

		return (args) -> {

			if (template.collectionExists(COLLECTION)) {
				template.dropCollection(COLLECTION);
			}

			var index = new GeospatialIndex("homePlanet.coordinates") //
					.typed(GeoSpatialIndexType.GEO_2DSPHERE) //
					.named("planet-coordinate-idx");

			template.createCollection(COLLECTION);
			template.indexOps(SWCharacter.class).ensureIndex(index);

			var alderaan = new Planet("alderaan", new Point(-73.9667, 40.78));
			var stewjon = new Planet("stewjon", new Point(-73.9836, 40.7538));
			var tatooine = new Planet("tatooine", new Point(-73.9928, 40.7193));

			var anakin = new Jedi("anakin", "skywalker");
			anakin.setHomePlanet(tatooine);

			var luke = new Jedi("luke", "skywalker");
			luke.setHomePlanet(tatooine);

			var leia = new Jedi("leia", "organa");
			leia.setHomePlanet(alderaan);

			var obiWan = new Jedi("obi-wan", "kenobi");
			obiWan.setHomePlanet(stewjon);

			var han = new Human("han", "solo");

			template.save(anakin, COLLECTION);
			template.save(luke, COLLECTION);
			template.save(leia, COLLECTION);
			template.save(obiWan, COLLECTION);
			template.save(han, COLLECTION);
		};
	}
}
