/*
 * Copyright 2014-2021 the original author or authors.
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
package example.springdata.rest.projections;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * @author Oliver Gierke
 */
@Entity(name = "SampleOrder")
@Data
public class Order {

	@Id @GeneratedValue //
	private Long id;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) //
	private List<LineItem> items = new ArrayList<>();

	@ManyToOne //
	private Customer customer;

	public Order add(LineItem item) {

		this.items.add(item);
		return this;
	}
}
