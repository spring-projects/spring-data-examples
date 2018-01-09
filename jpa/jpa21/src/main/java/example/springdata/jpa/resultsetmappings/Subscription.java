/*
 * Copyright 2018 the original author or authors.
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
package example.springdata.jpa.resultsetmappings;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;

/**
 * @author Thomas Darimont
 */
@Entity
@NoArgsConstructor
@SqlResultSetMapping( //
        name="subscriptionSummary", //
        classes = @ConstructorResult(
                targetClass = SubscriptionSummary.class, //
                columns={
                        @ColumnResult(name="productName", type=String.class), //
                        @ColumnResult(name="subscriptions", type=long.class)
                }))
@NamedNativeQuery(
        name="Subscription.findAllSubscriptionSummaries", //
        query="select product_name as productName, count(user_id) as subscriptions from subscription group by product_name order by productName", //
        resultSetMapping = "subscriptionSummary")
@Data
public class Subscription {

    @Id
    @GeneratedValue
    Long id;

    String productName;

    long userId;

    public Subscription(String productName, long userId) {
        this.productName = productName;
        this.userId = userId;
    }
}
