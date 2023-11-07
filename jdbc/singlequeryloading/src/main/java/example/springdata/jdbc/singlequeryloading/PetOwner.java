/*
 * Copyright 2023 the original author or authors.
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
package example.springdata.jdbc.singlequeryloading;

import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;

/**
 * An aggregate with mutliple collections.
 *
 * @author Jens Schauder
 */
class PetOwner {

	@Id Long Id;

	String name;

	List<Dog> dogs;

	List<Cat> cats;

	List<Fish> fish;

	public PetOwner(String name, List<Cat> cats, List<Dog> dogs, List<Fish> fish) {

		this.name = name;
		this.cats = cats;
		this.dogs = dogs;
		this.fish = fish;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		PetOwner petOwner = (PetOwner) o;
		return Objects.equals(Id, petOwner.Id) && Objects.equals(name, petOwner.name) && Objects.equals(dogs, petOwner.dogs)
				&& Objects.equals(cats, petOwner.cats) && Objects.equals(fish, petOwner.fish);
	}

	@Override
	public int hashCode() {
		return Objects.hash(Id, name, dogs, cats, fish);
	}
}
