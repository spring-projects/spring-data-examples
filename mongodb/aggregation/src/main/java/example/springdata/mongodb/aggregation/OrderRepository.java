/*
 * Copyright 2013-2021 the original author or authors.
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
package example.springdata.mongodb.aggregation;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.repository.CrudRepository;

/**
 * A repository interface assembling CRUD functionality as well as the API to invoke the methods implemented manually.
 *
 * @author Thomas Darimont
 * @author Oliver Gierke
 * @author Divya Srivastava
 */
public interface OrderRepository extends CrudRepository<Order, String>, OrderRepositoryCustom {

	@Aggregation("{ $group : { _id : $customerId, total : { $sum : 1 } } }")
	List<OrdersPerCustomer> totalOrdersPerCustomer(Sort sort);

	@Aggregation(pipeline = { "{ $match : { customerId : ?0 } }", "{ $count : total }" })
	Long totalOrdersForCustomer(String customerId);

	@Aggregation(pipeline = { "{ $match : { id : ?0 } }", "{ $unwind : { path : '$items' } }",
			"{ $project : { id : 1 , customerId : 1 , items : 1 , lineTotal : { $multiply: [ '$items.price' , '$items.quantity' ] } } }",
			"{ $group : { '_id' : '$_id' , 'netAmount' : { $sum : '$lineTotal' } , 'items' : { $addToSet : '$items' } } }",
			"{ $project : { 'orderId' : '$_id' , 'items' : 1 , 'netAmount' : 1 , 'taxAmount' : { $multiply: [ '$netAmount' , 0.19 ] } , 'totalAmount' : { $multiply: [ '$netAmount' , 1.19 ] } } }" })
	Invoice aggregateInvoiceForOrder(String orderId);

}
