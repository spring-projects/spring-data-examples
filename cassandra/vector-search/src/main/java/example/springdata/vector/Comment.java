/*
 * Copyright 2025 the original author or authors.
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
package example.springdata.vector;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.SaiIndexed;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.VectorType;
import org.springframework.data.domain.Vector;

/**
 * Sample entity containing a {@link Vector vector} {@link #embedding}.
 */
@Table
public class Comment {

    @Id
    private String id;

    private String country;
    private String description;

    @SaiIndexed
    @VectorType(dimensions = 5)
    private Vector embedding;

    public Comment() {
    }

    public Comment(String country, String description, Vector embedding) {
        this.id = UUID.randomUUID().toString();
        this.country = country;
        this.description = description;
        this.embedding = embedding;
    }

    public static Comment of(Comment source) {
        return new Comment(source.getCountry(), source.getDescription(), source.getEmbedding());
    }

    public String getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public String getDescription() {
        return description;
    }

    public Vector getEmbedding() {
        return embedding;
    }

    @Override
    public String toString() {
        return "%s (%s)".formatted(getDescription(), getCountry());
    }
}
