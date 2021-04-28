/*
 * Copyright 2017-2021 the original author or authors.
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
package example.springdata.jdbc.basics.aggregate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.With;
import lombok.With;

import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.AccessType.Type;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;

/**
 * A Lego Set consisting of multiple Blocks and a manual
 *
 * @author Jens Schauder
 */
@Data
@AccessType(Type.PROPERTY)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class LegoSet {

	private @Id int id;
	private String name;
	private @Transient Period minimumAge, maximumAge;

	/**
	 * Since Manuals are part of a {@link LegoSet} and only make sense inside a {@link LegoSet} it is considered part of
	 * the Aggregate.
	 */
	@Column("HANDBUCH_ID")
	private Manual manual;

	// You can build multiple models from one LegoSet
	@MappedCollection(keyColumn = "NAME")
	private final @AccessType(Type.FIELD) @With(AccessLevel.PACKAGE) Map<String, Model> models;

	LegoSet() {
		this.models = new HashMap<>();
	}

	// conversion for custom types currently has to be done through getters/setter + marking the underlying property with
	// @Transient.
	@Column("MIN_AGE")
	public int getIntMinimumAge() {
		return toInt(this.minimumAge);
	}

	public void setIntMinimumAge(int years) {
		minimumAge = toPeriod(years);
	}

	@Column("MAX_AGE")
	public int getIntMaximumAge() {
		return toInt(this.maximumAge);
	}

	public void setIntMaximumAge(int years) {
		maximumAge = toPeriod(years);
	}

	private static int toInt(Period period) {
		return (int) (period == null ? 0 : period.get(ChronoUnit.YEARS));
	}

	private static Period toPeriod(int years) {
		return Period.ofYears(years);
	}

	public void addModel(String name, String description) {

		var model = new Model(name, description);
		models.put(name, model);
	}
}
