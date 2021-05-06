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

import java.util.List;

import org.immutables.value.Value;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.Nullable;

/**
 * @author Mark Paluch
 */
@Value.Immutable
@Table("ENIGMA")
public interface Enigma {

	@Nullable
	@Id
	Long getId();

	String getModel();

	// Explicit keys to not derive key names from the implementation class.
	@MappedCollection(idColumn = "ENIGMA_ID", keyColumn = "ROTOR_KEY")
	List<Rotor> getRotors();

}
