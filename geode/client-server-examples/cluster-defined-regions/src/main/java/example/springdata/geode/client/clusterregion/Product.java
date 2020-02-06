/*
 *
 *  * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 *  * agreements. See the NOTICE file distributed with this work for additional information regarding
 *  * copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the
 *  * "License"); you may not use this file except in compliance with the License. You may obtain a
 *  * copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software distributed under the License
 *  * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *  * or implied. See the License for the specific language governing permissions and limitations under
 *  * the License.
 *
 */

package example.springdata.geode.client.clusterregion;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.gemfire.mapping.annotation.Region;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * A product used in the examples.
 *
 * @author Oliver Gierke
 * @author David Turanski
 * @author Udo Kohlmeyer
 * @author Patrick Johnson
 */
@Region("Products")
public class Product implements Serializable {

	@Id
	private Long id;
	private String name;
	private BigDecimal price;
	private String description;

	@Transient
	private Map<String, String> attributes = new HashMap<>();

	@PersistenceConstructor
	public Product(Long id, String name, BigDecimal price, String description) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
	}

	/**
	 * Sets the attribute with the given name to the given value.
	 *
	 * @param name  must not be null or empty.
	 * @param value
	 */
	public void addAttribute(String name, String value) {
		this.attributes.put(name, value);
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	@Override
	public String toString() {
		return name + " @ " + price;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public String getName() {
		return name;
	}
}
