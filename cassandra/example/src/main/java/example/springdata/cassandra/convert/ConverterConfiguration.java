/*
 * Copyright 2016-2021 the original author or authors.
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
package example.springdata.cassandra.convert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.cassandra.core.convert.CassandraCustomConversions;
import org.springframework.util.StringUtils;

import com.datastax.oss.driver.api.core.cql.Row;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * {@link Configuration} class to register custom converters.
 *
 * @author Mark Paluch
 */
@SpringBootApplication
@EntityScan(basePackageClasses = Addressbook.class)
class ConverterConfiguration {

	@Bean
	public CassandraCustomConversions customConversions() {

		List<Converter<?, ?>> converters = new ArrayList<>();
		converters.add(new PersonWriteConverter());
		converters.add(new PersonReadConverter());
		converters.add(new CustomAddressbookReadConverter());
		converters.add(CurrencyToStringConverter.INSTANCE);
		converters.add(StringToCurrencyConverter.INSTANCE);

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

			return new CustomAddressbook(source.getString("id"), source.getString("me"));
		}
	}

	enum StringToCurrencyConverter implements Converter<String, Currency> {
		INSTANCE;

		@Override
		public Currency convert(String source) {
			return Currency.getInstance(source);
		}
	}

	enum CurrencyToStringConverter implements Converter<Currency, String> {

		INSTANCE;

		@Override
		public String convert(Currency source) {
			return source.getCurrencyCode();
		}
	}
}
