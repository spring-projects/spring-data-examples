/*
 * Copyright 2021 the original author or authors.
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
package example.springdata.jdbc.immutables;

import org.immutables.value.Value;

import org.springframework.data.relational.core.mapping.Table;

/**
 * @author Mark Paluch
 */
@Value.Immutable
@Table("ROTOR")
public interface Rotor {

	String getName();

	String getWiring();

	char getNotch();

	/**
	 * Factory method for {@link Rotor} as using {@code @Value.Style} and {@code @Value.Parameter} conflicts with Spring
	 * Data's constructor discovery rules.
	 *
	 * @param name The name of the rotor, just a label to distinguish them.
	 * @param wiring A String consisting of all letters of the alphabet encoding which input letter is connected to which output letter.
	 * @param notch The current position of the rotor.
	 * @return a newly created Rotor.
	 */
	static Rotor of(String name, String wiring, char notch) {
		return ImmutableRotor.builder().name(name).wiring(wiring).notch(notch).build();
	}

}
