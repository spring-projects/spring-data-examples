/*
 * Copyright 2014-2021 the original author or authors.
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
package example.springdata.rest.stores;

import example.springdata.rest.stores.Store.Address;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.separator.DefaultRecordSeparatorPolicy;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.index.IndexResolver;
import org.springframework.stereotype.Component;

/**
 * Component initializing a hand full of Starbucks stores and persisting them through a {@link StoreRepository}.
 *
 * @author Oliver Gierke
 * @author Mark Paluch
 */
@Slf4j
@Component
public class StoreInitializer {

	@Autowired
	public StoreInitializer(StoreRepository repository, MongoOperations operations) throws Exception {

		if (repository.count() != 0) {
			return;
		}

		var indexDefinitions = IndexResolver.create(operations.getConverter().getMappingContext())
				.resolveIndexFor(Store.class);

		indexDefinitions.forEach(operations.indexOps(Store.class)::ensureIndex);

		var stores = readStores();
		log.info("Importing {} stores into MongoDB…", stores.size());
		repository.saveAll(stores);
		log.info("Successfully imported {} stores.", repository.count());
	}

	/**
	 * Reads a file {@code starbucks.csv} from the class path and parses it into {@link Store} instances about to
	 * persisted.
	 *
	 * @return
	 * @throws Exception
	 */
	private static List<Store> readStores() throws Exception {

		var resource = new ClassPathResource("starbucks.csv");
		var scanner = new Scanner(resource.getInputStream());
		var line = scanner.nextLine();
		scanner.close();

		var itemReader = new FlatFileItemReader<Store>();
		itemReader.setResource(resource);

		// DelimitedLineTokenizer defaults to comma as its delimiter
		var tokenizer = new DelimitedLineTokenizer();
		tokenizer.setNames(line.split(","));
		tokenizer.setStrict(false);

		var lineMapper = new DefaultLineMapper<Store>();
		lineMapper.setFieldSetMapper(fields -> {

			var location = new Point(fields.readDouble("Longitude"), fields.readDouble("Latitude"));
			var address = new Address(fields.readString("Street Address"), fields.readString("City"),
					fields.readString("Zip"), location);

			return new Store(UUID.randomUUID(), fields.readString("Name"), address);
		});

		lineMapper.setLineTokenizer(tokenizer);
		itemReader.setLineMapper(lineMapper);
		itemReader.setRecordSeparatorPolicy(new DefaultRecordSeparatorPolicy());
		itemReader.setLinesToSkip(1);
		itemReader.open(new ExecutionContext());

		List<Store> stores = new ArrayList<>();
		Store store = null;

		do {

			store = itemReader.read();

			if (store != null) {
				stores.add(store);
			}

		} while (store != null);

		return stores;
	}
}
