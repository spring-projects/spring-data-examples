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
package example.springdata.vector;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.Array;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Sample entity containing a {@link SqlTypes#VECTOR vector} {@link #embedding}.
 */
@Entity
@Table(name = "jpa_comment")
public class Comment {

	@Id
	@GeneratedValue private Long id;

	private String country;
	private String description;

	@JdbcTypeCode(SqlTypes.VECTOR)
	@Array(length = 5) private float[] embedding;

	public Comment() {}

	public Comment(String country, String description, float[] embedding) {
		this.country = country;
		this.description = description;
		this.embedding = embedding;
	}

	public static Comment of(Comment source) {
		return new Comment(source.getCountry(), source.getDescription(), source.getEmbedding());
	}

	public long getId() {
		return id;
	}

	public String getCountry() {
		return country;
	}

	public String getDescription() {
		return description;
	}

	public float[] getEmbedding() {
		return embedding;
	}

	@Override
	public String toString() {
		return "%s (%s)".formatted(getDescription(), getCountry());
	}
}
