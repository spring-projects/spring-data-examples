/*
 * Copyright 2020-2021 the original author or authors.
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
package example.springdata.geode.server.events;

import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.geode.cache.CacheLoader;
import org.apache.geode.cache.CacheLoaderException;
import org.apache.geode.cache.LoaderHelper;

import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

/**
 * @author Patrick Johnson
 */
@Component
public class ProductCacheLoader implements CacheLoader<Long, Product> {
	private Faker faker = new Faker();

	@Override
	public Product load(LoaderHelper loaderHelper) throws CacheLoaderException {
		return new Product((long) loaderHelper.getKey(), randomStringName(), randomPrice(), "");
	}

	@SneakyThrows
	private BigDecimal randomPrice() {
		return new BigDecimal(new DecimalFormat("#0.00").parse(faker.commerce().price()).toString());
	}

	private String randomStringName() {
		return faker.commerce().productName();
	}
}
