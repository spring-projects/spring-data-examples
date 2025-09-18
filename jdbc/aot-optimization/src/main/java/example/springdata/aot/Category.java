/*
 * Copyright 2025 the original author or authors.
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
package example.springdata.aot;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;

/**
 * Coarse classification.
 *
 * @author Jens Schauder
 */
public class Category {

	private final @Id Long id;
	private String name, description;
	private LocalDateTime created;
	private Long inserted;

	public Category(String name, String description) {

		this.id = null;
		this.name = name;
		this.description = description;
		this.created = LocalDateTime.now();
	}

	@PersistenceCreator
	Category(Long id, String name, String description, LocalDateTime created, Long inserted) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.created = created;
		this.inserted = inserted;
	}

	public void timeStamp() {

		if (inserted == 0) {
			inserted = System.currentTimeMillis();
		}
	}

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public LocalDateTime getCreated() {
		return this.created;
	}

	public Long getInserted() {
		return this.inserted;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public void setInserted(Long inserted) {
		this.inserted = inserted;
	}

	public Category withId(Long id) {
		return this.id == id ? this : new Category(id, this.name, this.description, this.created, this.inserted);
	}

	@Override
	public String toString() {
		return "Category(id=" + this.getId() + ", name=" + this.getName() + ", description=" + this.getDescription()
				+ ", created=" + this.getCreated() + ", inserted=" + this.getInserted() + ")";
	}
}
