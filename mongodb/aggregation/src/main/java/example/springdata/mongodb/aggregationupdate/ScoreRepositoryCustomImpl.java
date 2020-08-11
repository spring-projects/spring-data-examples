/*
 * Copyright 2020 the original author or authors.
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
package example.springdata.mongodb.aggregationupdate;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.AggregationUpdate;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import org.springframework.data.mongodb.core.aggregation.SetOperation;
import org.springframework.stereotype.Component;

/**
 * @author Christoph Strobl
 */
@Component
class ScoreRepositoryCustomImpl implements ScoreRepositoryCustom {

	private final MongoOperations mongoOperations;

	ScoreRepositoryCustomImpl(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}

	@Override
	public void calculateTotalScore() {

		AggregationUpdate update = AggregationUpdate.update().set(SetOperation.builder() //
				.set("totalHomework").toValueOf(ArithmeticOperators.valueOf("homework").sum()).and() //
				.set("totalQuiz").toValueOf(ArithmeticOperators.valueOf("quiz").sum())) //
				.set(SetOperation.builder() //
						.set("totalScore")
						.toValueOf(ArithmeticOperators.valueOf("totalHomework").add("totalQuiz").add("extraCredit")));

		mongoOperations.update(Score.class).apply(update).all();
	}
}
