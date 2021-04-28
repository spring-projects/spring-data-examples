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
package example.springdata.mongodb.util;

import lombok.extern.slf4j.Slf4j;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.repository.init.Jackson2ResourceReader;
import org.springframework.util.Assert;

import example.springdata.mongodb.textsearch.BlogPost;

/**
 * Component to initialize {@link BlogPost}s by accessing the latest ones from the Spring blog.
 *
 * @author Christoph Strobl
 * @author Oliver Gierke
 */
@Slf4j
public enum BlogPostInitializer {

	INSTANCE;

	/**
	 * Initializes the given {@link MongoOperations} with {@link BlogPost}s from the Spring Blog.
	 *
	 * @param operations must not be {@literal null}.
	 * @throws Exception
	 */
	public void initialize(MongoOperations operations) throws Exception {

		Assert.notNull(operations, "MongoOperations must not be null!");
		loadFromClasspathSource(operations);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void loadFromClasspathSource(MongoOperations operations) throws Exception {

		var reader = new Jackson2ResourceReader();

		var source = reader.readFrom(new ClassPathResource("spring-blog.atom.json"), this.getClass().getClassLoader());

		if (source instanceof Iterable) {
			((Iterable) source).forEach(operations::save);
		} else {
			operations.save(source);
		}

		log.info("Imported blog posts from classpath!");
	}
}
