/*
 * Copyright 2023 the original author or authors.
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
package example.springdata.jdbc.howto.caching;

import liquibase.database.Database;
import liquibase.database.core.HsqlDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.jdbc.core.mapping.schema.DefaultSqlTypeMapping;
import org.springframework.data.jdbc.core.mapping.schema.LiquibaseChangeSetWriter;
import org.springframework.data.jdbc.core.mapping.schema.SqlTypeMapping;
import org.springframework.data.relational.core.mapping.RelationalMappingContext;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;

/**
 * Example code demonstrating how to use the Schema Generation Feature.
 *
 * @author Jens Schauder
 * @since 3.2
 */
@SpringBootTest
class SchemaGenerationTest {

	@Autowired
	RelationalMappingContext context;

	@Autowired
	DataSource ds;

	@Test
	void minimumExample() throws IOException {

		// the change set will get appended, so we delete any pre existing file.
		new File("cs-minimum.yaml").delete();

		context.setInitialEntitySet(Collections.singleton(Minion.class));
		LiquibaseChangeSetWriter writer = new LiquibaseChangeSetWriter(context);

		writer.writeChangeSet(new FileSystemResource("cs-minimum.yaml"));
	}


	@Test
	void diffing() {

		// the change set will get appended, so we delete any pre existing file.
		new File("cs-diff.yaml").delete();

		context.setInitialEntitySet(Collections.singleton(Minion.class));
		LiquibaseChangeSetWriter writer = new LiquibaseChangeSetWriter(context);

		// drop unused columns
		writer.setDropColumnFilter((table, column) -> !column.equalsIgnoreCase("special"));

		// for comparison with existing schema
		try (Database db = new HsqlDatabase()) {

			db.setConnection(new JdbcConnection(ds.getConnection()));

			writer.writeChangeSet(new FileSystemResource("cs-diff.yaml"), db);

		} catch (IOException | SQLException | LiquibaseException e) {
			throw new RuntimeException("Changeset generation failed", e);
		}
	}

	@Test
	void customizingTypes() throws IOException {

		// the change set will get appended, so we delete any pre existing file.
		new File("cs-custom.yaml").delete();

		context.setInitialEntitySet(Collections.singleton(Minion.class));
		LiquibaseChangeSetWriter writer = new LiquibaseChangeSetWriter(context);

		writer.setSqlTypeMapping(((SqlTypeMapping) property -> {
			if (property.getName().equalsIgnoreCase("name")) {
				return "VARCHAR(500)";
			}
			return null;
		}).and(new DefaultSqlTypeMapping()));

		writer.writeChangeSet(new FileSystemResource("cs-custom.yaml"));

	}

	@Test
	void customizingTypesUsingAnnotations() throws IOException {

		// the change set will get appended, so we delete any pre existing file.
		new File("cs-annotation.yaml").delete();

		context.setInitialEntitySet(Collections.singleton(Minion.class));
		LiquibaseChangeSetWriter writer = new LiquibaseChangeSetWriter(context);

		writer.setSqlTypeMapping(((SqlTypeMapping) property -> {

			if (!property.getType().equals(String.class)) {
				return null;
			}

			// findAnnotation will find meta annotations
			Varchar varchar = property.findAnnotation(Varchar.class);
			int value = varchar.value();

			if (varchar == null) {
				return null;
			}
			return "VARCHAR(" +
					varchar.value() +
					")";

		}).and(new DefaultSqlTypeMapping()));

		writer.writeChangeSet(new FileSystemResource("cs-annotation.yaml"));

	}
}
