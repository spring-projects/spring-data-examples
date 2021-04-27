/*
 * Copyright 2020-2021 the original author or authors.
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
package example.springdata.geode.server.expiration.eviction;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.gemfire.mapping.annotation.Region;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A product used in the examples.
 *
 * @author Oliver Gierke
 * @author David Turanski
 * @author Udo Kohlmeyer
 * @author Patrick Johnson
 */
@Data
@Region("Products")
public class Product implements Serializable {

	@Id
	private Long id;
	private String name;
	private BigDecimal price;
	private String description;

	@PersistenceConstructor
	public Product(Long id, String name, BigDecimal price, String description) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
	}
}
