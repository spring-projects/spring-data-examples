/*
 * Copyright 2021 the original author or authors.
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
package example.springdata.jdbc.howto.selectiveupdate;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Version;


/**
 * A minion. The main entity and aggregate root for this example.
 *
 * @author Jens Schauder
 */
class Minion {

	@Id Long id;
	String name;
	Color color = Color.YELLOW;
	Set<Toy> toys = new HashSet<>();
	@Version int version;

	Minion(String name) {
		this.name = name;
	}

	@PersistenceConstructor
	private Minion(Long id, String name, Collection<Toy> toys, int version) {

		this.id = id;
		this.name = name;
		this.toys.addAll(toys);
		this.version = version;
	}

	Minion addToy(Toy toy) {

		toys.add(toy);
		return this;
	}
}
