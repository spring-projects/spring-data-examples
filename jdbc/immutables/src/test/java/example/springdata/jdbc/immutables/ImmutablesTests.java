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

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

/**
 * Integration tests using immutable types assisted by Immutables. Calling code can use the actual immutable
 * interface/abstract class while Spring Data materializes the actual implementation.
 *
 * @author Mark Paluch
 * @see <a href="https://immutables.github.io/">https://immutables.github.io/</a>
 */
@DataJdbcTest
class ImmutablesTests {

	@Autowired EnigmaRepository repository;

	@Test
	void shouldInsertAndRetrieveObject() {

		var enigmaA = ImmutableEnigma.builder().model("A") //
				.addRotors(Rotor.of("I", "DMTWSILRUYQNKFEJCAZBPGXOHV", 'Q')) //
				.addRotors(Rotor.of("II", "HQZGPJTMOBLNCIFDYAWVEUSRXL", 'E')) //
				.build();

		var saved = repository.save(enigmaA);

		assertThat(saved.getId()).isNotNull();

		var loaded = repository.findByModel("A");

		assertThat(loaded).isEqualTo(saved).isInstanceOf(ImmutableEnigma.class);
		assertThat(loaded.getRotors()).hasSize(2);
		assertThat(loaded.getRotors().get(0)).isInstanceOf(ImmutableRotor.class);
	}
}
