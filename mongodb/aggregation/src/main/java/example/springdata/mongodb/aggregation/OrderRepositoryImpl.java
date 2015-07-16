/*
 * Copyright 2014 the original author or authors.
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
package example.springdata.mongodb.aggregation;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.*;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

/**
 * The manual implementation parts for {@link OrderRepository}. This will automatically be picked up by the Spring Data
 * infrastructure as we follow the naming convention of extending the core repository interface's name with {@code Impl}
 * .
 * 
 * @author Thomas Darimont
 * @author Oliver Gierke
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class OrderRepositoryImpl implements OrderRepositoryCustom {

	private final MongoOperations operations;

	private double taxRate = 0.19;

	/**
	 * The implementation uses the MongoDB aggregation framework support Spring Data provides as well as SpEL expressions
	 * to define arithmetical expressions. Note how we work with property names only and don't have to mitigate the nested
	 * {@code $_id} fields MongoDB usually requires.
	 * 
	 * @see example.springdata.mongodb.aggregation.OrderRepositoryCustom#getInvoiceFor(example.springdata.mongodb.aggregation.Order)
	 */
	@Override
	public Invoice getInvoiceFor(Order order) {

		AggregationResults<Invoice> results = operations.aggregate(newAggregation(Order.class, //
				match(where("id").is(order.getId())), //
				unwind("items"), //
				project("id", "customerId", "items") //
						.andExpression("'$items.price' * '$items.quantity'").as("lineTotal"), //
				group("id") //
						.sum("lineTotal").as("netAmount") //
						.addToSet("items").as("items"), //
				project("id", "items", "netAmount") //
						.and("orderId").previousOperation() //
						.andExpression("netAmount * [0]", taxRate).as("taxAmount") //
						.andExpression("netAmount * (1 + [0])", taxRate).as("totalAmount") //
				), Invoice.class);

		return results.getUniqueMappedResult();
	}
}
