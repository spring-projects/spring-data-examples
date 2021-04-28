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

import static org.assertj.core.api.Assertions.*;
import static org.springframework.data.geo.Metrics.*;
import static org.springframework.data.mongodb.core.query.Criteria.*;
import static org.springframework.data.mongodb.core.query.Query.*;
import static org.springframework.data.mongodb.core.query.Update.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.mongodb.core.ExecutableFindOperation.TerminatingFind;
import org.springframework.data.mongodb.core.FluentMongoOperations;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.NearQuery;

/**
 * Some tests showing usage and capabilities of {@link FluentMongoOperations}. <br />
 * Please note index and testdata setup in {@link ApplicationConfiguration} with
 * <dl>
 * <dt>3 Planets</dt>
 * <dd>alderaan, stewjon, tatooine</dd>
 * <dt>4 Jedis</dt>
 * <dd>anakin, leia, luke, obi-wan</dd>
 * <dt>1 Human</dt>
 * <dd>han</dd>
 * </dl>
 *
 * @author Christoph Strobl
 */
@DataMongoTest
class FluentMongoApiTests {

	@Autowired FluentMongoOperations mongoOps;

	/**
	 * A predefined, reusable lookup method.
	 */
	private TerminatingFind<Jedi> findLuke;

	private final NearQuery alderaanWithin3Parsecs = NearQuery.near(-73.9667, 40.78).maxDistance(new Distance(3, MILES))
			.spherical(true);

	@BeforeEach
	void setUp() {

		findLuke = mongoOps.query(SWCharacter.class) // SWCharacter does only define the collection, id and name
				.as(Jedi.class) // so we use Jedi as the desired return type to also map "lastname"
				.matching(query(where("name").is("luke"))); // for all with a matching "name" that maps to "firstname".
	}

	/**
	 * Use the predefined lookup method {@link #findLuke} to query the {@literal star-wars} collection derived from
	 * {@link SWCharacter}. The originating domain type {@link SWCharacter} is used for mapping the query to
	 * {@link org.bson.Document}s which results in <code>{ firstname : "luke" }</code> as {@link SWCharacter#name} is
	 * mapped to {@literal firstname} via {@link org.springframework.data.mongodb.core.mapping.Field}. <br />
	 * For return type mapping {@link Jedi} is used which allows also reading back the {@link Jedi#lastname} next to the
	 * {@literal firstname}. <br />
	 * The samples below would read something like the following using classic {@link MongoOperations}.
	 *
	 * <pre>
	 *     <code>
	 *         This is not possible with classic MongoOperations.
	 *     </code>
	 * </pre>
	 */
	@Test
	void usePredefinedFinder() {
		assertThat(findLuke.one()).contains(new Jedi("luke", "skywalker"));
	}

	/**
	 * Using {@code as(java.lang.Class)} allows to not only map resulting {@link org.bson.Document}s into a given class
	 * but also creating interfaces, limiting access to properties. <br />
	 * {@link Sith#getName()} defines a SpEL expression for {@code firstname + " " + lastname} of the target object via
	 * {@link org.springframework.beans.factory.annotation.Value}.<br />
	 * The samples below would read something like the following using classic {@link MongoOperations}.
	 *
	 * <pre>
	 *     <code>
	 *         This is not possible with classic MongoOperations.
	 *     </code>
	 * </pre>
	 */
	@Test
	void fetchInterfaceProjection() {

		var anakin = mongoOps.query(SWCharacter.class) // SWCharacter does only define the collection, id and name
				.as(Sith.class) // use an interface as return type to create a projection
				.matching(query(where("firstname").is("anakin"))) // so properties are taken as is
				.oneValue();

		assertThat(anakin.getName()).isEqualTo("anakin skywalker");
	}

	/**
	 * The difference between the terminating methods {@link TerminatingFind#first()} and {@link TerminatingFind#one()} is
	 * that {@code fist()} returns the very first entry found or none at all. Therefore the query execution is limited to
	 * exactly {@code 1} element via {@link com.mongodb.client.FindIterable#limit(int)}; <br />
	 * {@code one()} on the other hand makes a hard assumption on cardinality of the query result and expects exactly one
	 * element to match the query or none at all. If more than one match is found {@code one()} raises an
	 * {@link IncorrectResultSizeDataAccessException}. Under the hood, the query execution is limited to {@code 2}
	 * elements. <br />
	 * The samples below would read something like the following using classic {@link MongoOperations}.
	 *
	 * <pre>
	 *     <code>
	 *         // find first();
	 *         return template.find(query(where("lastname").is("skywalker")).limit(1), SWCharacter.class, "star-wars")
	 *             .iterator()
	 *             .next();
	 *
	 *         // find one();
	 *         List result =  template.find(query(where("lastname").is("skywalker")).limit(2), SWCharacter.class, "star-wars");
	 *		   if(result.size() > 1) {
	 *		       throw new IncorrectResultSize...
	 *		   }
	 *		   return result.iterator().next();
	 *     </code>
	 * </pre>
	 */
	@Test
	void queryFirstVsOne() {

		mongoOps.query(SWCharacter.class) // SWCharacter does only define the collection, id and name
				.matching(query(where("lastname").is("skywalker"))) // so properties are taken as is
				.first(); // and we'll just get the first whatever entry if there is.

		assertThatExceptionOfType(IncorrectResultSizeDataAccessException.class) //
				.isThrownBy(() -> {

					mongoOps.query(SWCharacter.class) // SWCharacter does only define the collection, id and name
							.matching(query(where("lastname").is("skywalker"))) // so properties are taken as is
							.one(); // and we expect there is only one entry matching. Not more! But obviously there is.
				});
	}

	/**
	 * GeoNear operations can be executed via {@code near} which needs to be given a {@link NearQuery}. By doing so the
	 * API will from then on only provide methods suitable for executing a {@literal near} query. In this case options
	 * like {@code first()} or {@code one()} are no longer available. Even the return type for {@code all()} switches from
	 * {@link List} to {@link GeoResults}. <br />
	 * Still it is possible to map the {@code content} of a single {@link org.springframework.data.geo.GeoResult} to a
	 * different type using {@code as(Class)}. <br />
	 * The samples below would read something like the following using classic {@link MongoOperations}.
	 *
	 * <pre>
	 *     <code>
	 *         template.geoNear(alderaanWithin3Parsecs, SWCharacter.class, "star-wars", Jedi.class);
	 *     </code>
	 * </pre>
	 */
	@Test
	void geoNearQuery() {

		var results = mongoOps.query(SWCharacter.class) // SWCharacter defines collection, id and name
				.as(Jedi.class) // but we want to map the results to Jedi
				.near(alderaanWithin3Parsecs) // and find those with home planet near alderaan
				.all();

		assertThat(results.getContent()).hasSize(2);
	}

	/**
	 * In this case {@link Human} does not have an explicit {@link org.springframework.data.mongodb.core.mapping.Document}
	 * annotation which results in {@literal human} as the default collection name. Via {@code inCollection(String)} it is
	 * possible to override the default and set it to whatever collection should be queried. <br />
	 * As there is no different return type declared via {@code as(String)}, the originating domain type {@link Human} is
	 * used for both query and result mapping. <br />
	 * The sample below would read something like the following using classic {@link MongoOperations}.
	 *
	 * <pre>
	 *     <code>
	 *         template.find(query(where("lastname").is("skywalker")), Human.class, "star-wars");
	 *     </code>
	 * </pre>
	 */
	@Test
	void querySpecificCollection() {

		var skywalkers = mongoOps.query(Human.class) // Human does not define a collection via @Document
				.inCollection("star-wars") // so we set an explicit collection name
				.matching(query(where("lastname").is("skywalker"))) // to find all documents with a matching "lastname"
				.all();

		assertThat(skywalkers).containsExactlyInAnyOrder(new Human("anakin", "skywalker"), new Human("luke", "skywalker"));
	}

	/**
	 * Simple insert operation adding a new {@link Jedi} to the {@literal star-wars} collection.
	 */
	@Test
	void justInsertOne() {

		var chewbacca = new SWCharacter("Chewbacca");

		mongoOps.insert(SWCharacter.class).one(chewbacca);

		assertThat(chewbacca.getId()).isNotBlank();
	}

	/**
	 * {@link FluentMongoOperations#update(Class)} defines the entry point for performing modifications on potentially
	 * already existing document without replacing the entire document. The domain type is used for both mapping the query
	 * identifying the potential update candidates as well as the property mapping for the
	 * {@link org.springframework.data.mongodb.core.query.Update} itself. <br />
	 * The sample below would read something like the following using classic {@link MongoOperations}.
	 *
	 * <pre>
	 *     <code>
	 *         template.upsert(query(where("lastname").is("windu")), update("name", "mence"), Jedi.class, "star-wars");
	 *     </code>
	 * </pre>
	 */
	@Test
	void updateAndUpsert() {

		var result = mongoOps.update(Jedi.class) // Jedi defines the collection and field mapping
				.matching(query(where("lastname").is("windu"))) // so "last" maps to "lastname".
				.apply(update("name", "mence")) // We'll update the "firstname" to "mence"
				.upsert(); // and add a new document if it does not exist already.

		assertThat(result.getMatchedCount()).isEqualTo(0);
		assertThat(result.getUpsertedId()).isNotNull();

		assertThat(
				mongoOps.query(Human.class).inCollection("star-wars").matching(query(where("firstname").is("mence"))).one())
						.contains(new Human("mence", "windu"));
	}
}
