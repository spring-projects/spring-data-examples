/*
 * Copyright 2015-2021 the original author or authors.
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
package example.users;

import static org.springframework.util.StringUtils.*;

import example.users.User.Address;
import example.users.User.Picture;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.separator.DefaultRecordSeparatorPolicy;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

/**
 * Initialize {@link UserRepository} with sample data.
 *
 * @author Christoph Strobl
 * @author Oliver Gierke
 */
@RequiredArgsConstructor
public class UserInitializer {

	private final UserRepository repository;

	public void init() throws Exception {

		List<User> users = readUsers(new ClassPathResource("randomuser.me.csv"));

		repository.deleteAll();
		repository.saveAll(users);
	}

	private static List<User> readUsers(Resource resource) throws Exception {

		Scanner scanner = new Scanner(resource.getInputStream());
		String line = scanner.nextLine();
		scanner.close();

		FlatFileItemReader<User> reader = new FlatFileItemReader<User>();
		reader.setResource(resource);

		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setNames(line.split(","));
		tokenizer.setStrict(false);

		DefaultLineMapper<User> lineMapper = new DefaultLineMapper<User>();
		lineMapper.setFieldSetMapper(fields -> {

			User user = new User();

			user.setEmail(fields.readString("email"));
			user.setFirstname(capitalize(fields.readString("first")));
			user.setLastname(capitalize(fields.readString("last")));
			user.setNationality(fields.readString("nationality"));

			String city = Arrays.stream(fields.readString("city").split(" "))//
					.map(StringUtils::capitalize)//
					.collect(Collectors.joining(" "));
			String street = Arrays.stream(fields.readString("street").split(" "))//
					.map(StringUtils::capitalize)//
					.collect(Collectors.joining(" "));

			try {
				user.setAddress(new Address(city, street, fields.readString("zip")));
			} catch (IllegalArgumentException e) {
				user.setAddress(new Address(city, street, fields.readString("postcode")));
			}

			user.setPicture(
					new Picture(fields.readString("large"), fields.readString("medium"), fields.readString("thumbnail")));
			user.setUsername(fields.readString("username"));
			user.setPassword(fields.readString("password"));

			return user;
		});

		lineMapper.setLineTokenizer(tokenizer);

		reader.setLineMapper(lineMapper);
		reader.setRecordSeparatorPolicy(new DefaultRecordSeparatorPolicy());
		reader.setLinesToSkip(1);
		reader.open(new ExecutionContext());

		List<User> users = new ArrayList<>();
		User user = null;

		do {

			user = reader.read();

			if (user != null) {
				users.add(user);
			}

		} while (user != null);

		return users;
	}
}
