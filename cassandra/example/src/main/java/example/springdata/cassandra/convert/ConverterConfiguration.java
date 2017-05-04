/*
 * Copyright 2016 the original author or authors.
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
package example.springdata.cassandra.convert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.convert.CassandraCustomConversions;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.util.StringUtils;

import com.datastax.driver.core.Row;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * {@link Configuration} class to register custom converters.
 * 
 * @author Mark Paluch
 */
@Configuration
@EnableCassandraRepositories
class ConverterConfiguration extends AbstractCassandraConfiguration {

	@Override
	public String getKeyspaceName() {
		return "example";
	}

	@Override
	public String[] getEntityBasePackages() {
		return new String[] { Addressbook.class.getPackage().getName() };
	}

	@Override
	public SchemaAction getSchemaAction() {
		return SchemaAction.RECREATE;
	}

	@Override
	public CassandraCustomConversions customConversions() {

		List<Converter<?, ?>> converters = new ArrayList<>();
		converters.add(new PersonWriteConverter());
		converters.add(new PersonReadConverter());
		converters.add(new CustomAddressbookReadConverter());

		return new CassandraCustomConversions(converters);
	}

	/**
	 * Write a {@link Contact} into its {@link String} representation.
	 */
	static class PersonWriteConverter implements Converter<Contact, String> {

		public String convert(Contact source) {

			try {
				return new ObjectMapper().writeValueAsString(source);
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}
	}

	/**
	 * Read a {@link Contact} from its {@link String} representation.
	 */
	static class PersonReadConverter implements Converter<String, Contact> {

		public Contact convert(String source) {

			if (StringUtils.hasText(source)) {
				try {
					return new ObjectMapper().readValue(source, Contact.class);
				} catch (IOException e) {
					throw new IllegalStateException(e);
				}
			}
			return null;
		}
	}

	/**
	 * Perform custom mapping by reading a {@link Row} into a custom class.
	 */
	static class CustomAddressbookReadConverter implements Converter<Row, CustomAddressbook> {

		public CustomAddressbook convert(Row source) {

			CustomAddressbook result = new CustomAddressbook();

			result.setTheId(source.getString("id"));
			result.setMyDetailsAsJson(source.getString("me"));

			return result;
		}
	}
}
