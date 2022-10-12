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
package com.example.data.jpa;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.example.data.jpa.model.Author;
import com.example.data.jpa.model.Book;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class CLR implements CommandLineRunner {

	private final AuthorRepository authorRepository;

	CLR(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}

	@Override
	@Transactional
	public void run(String... args) {
		var authors = insertAuthors();
		listAllAuthors();
		findById(authors);
		findByPartialName();
		queryFindByName();
		deleteAll();
	}

	private void deleteAll() {
		this.authorRepository.deleteAll();
		long count = this.authorRepository.count();
		System.out.printf("deleteAll(): count = %d%n", count);
	}

	private void queryFindByName() {
		Author author1 = this.authorRepository.queryFindByName("Josh Long").orElse(null);
		Author author2 = this.authorRepository.queryFindByName("Martin Kleppmann").orElse(null);

		System.out.printf("queryFindByName(): author1 = %s%n", author1);
		System.out.printf("queryFindByName(): author2 = %s%n", author2);
	}

	private void findByPartialName() {
		Author author1 = this.authorRepository.findByNameContainingIgnoreCase("sh lo").orElse(null);
		Author author2 = this.authorRepository.findByNameContainingIgnoreCase("in kl").orElse(null);

		System.out.printf("findByPartialName(): author1 = %s%n", author1);
		System.out.printf("findByPartialName(): author2 = %s%n", author2);
	}

	private void findById(List<Author> authors) {
		Author author1 = this.authorRepository.findById(authors.get(0).getId()).orElse(null);
		Author author2 = this.authorRepository.findById(authors.get(1).getId()).orElse(null);

		System.out.printf("findById(): author1 = %s%n", author1);
		System.out.printf("findById(): author2 = %s%n", author2);
	}

	private void listAllAuthors() {
		List<Author> authors = this.authorRepository.findAll();
		for (Author author : authors) {
			System.out.printf("listAllAuthors(): author = %s%n", author);
			for (Book book : author.getBooks()) {
				System.out.printf("\t%s%n", book);
			}
		}
	}

	private List<Author> insertAuthors() {
		Author author1 = this.authorRepository.save(new Author(null, "Josh Long",
				Set.of(new Book(null, "Reactive Spring"), new Book(null, "Cloud Native Java"))));
		Author author2 = this.authorRepository.save(
				new Author(null, "Martin Kleppmann", Set.of(new Book(null, "Designing Data Intensive Applications"))));

		System.out.printf("insertAuthors(): author1 = %s%n", author1);
		System.out.printf("insertAuthors(): author2 = %s%n", author2);

		return Arrays.asList(author1, author2);
	}

}
