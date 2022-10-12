/*
 * Copyright 2022 the original author or authors.
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
package com.example.data.jpa.model;

import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Author {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@OneToMany(cascade = CascadeType.ALL)
	private Set<Book> books;

	protected Author() {
	}

	public Author(Long id, String name, Set<Book> books) {
		this.id = id;
		this.name = name;
		this.books = books;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Book> getBooks() {
		return books;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Author author = (Author) o;
		return Objects.equals(id, author.id) && Objects.equals(name, author.name)
				&& Objects.equals(books, author.books);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, books);
	}

	@Override
	public String toString() {
		return "Author{" + "name='" + name + '\'' + '}';
	}

}
