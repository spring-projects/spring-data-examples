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

import java.util.Objects;

/**
 * Toys for minions to play with.
 *
 * @author Jens Schauder
 */
class Toy {
	String name;

	Toy(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Toy toy = (Toy) o;
		return Objects.equals(name, toy.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}
