/*
 * Copyright 2014 the original author or authors.
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
package example.springdata.mongodb.util;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.rometools.rome.feed.atom.Category;
import com.rometools.rome.feed.atom.Content;
import com.rometools.rome.feed.atom.Entry;
import com.rometools.rome.feed.atom.Feed;

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

	private final RestTemplate restTemplate = new RestTemplate();
	private final Converter<Entry, BlogPost> converter = EntryConverter.INSTANCE;
	private final String url = "https://spring.io/blog.atom";

	/**
	 * Initializes the given {@link MongoOperations} with {@link BlogPost}s from the Spring Blog.
	 * 
	 * @param operations must not be {@literal null}.
	 */
	public void initialize(MongoOperations operations) {

		Assert.notNull(operations, "MongoOperations must not be null!");

		ResponseEntity<Feed> feed = restTemplate.getForEntity(url, Feed.class);
		int count = 0;

		if (feed.hasBody()) {
			for (Object entry : feed.getBody().getEntries()) {
				if (entry instanceof Entry) {
					operations.save(converter.convert((Entry) entry));
					count++;
				}
			}
		}

		log.info("Imported {} blog posts from spring.io!", count);
	}

	/**
	 * {@link Converter} implementation capable of converting atom feed {@link Entry} into {@link BlogPost}.
	 * 
	 * @author Christoph Strobl
	 * @author Oliver Gierke
	 */
	private enum EntryConverter implements Converter<Entry, BlogPost> {

		INSTANCE;

		/*
		 * (non-Javadoc)
		 * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
		 */
		@Override
		public BlogPost convert(Entry source) {

			BlogPost post = new BlogPost();

			post.setId(source.getId());
			post.setTitle(source.getTitle());

			for (Object content : source.getContents()) {
				if (content instanceof Content) {
					post.setContent(((Content) content).getValue());
				}
			}

			List<String> categories = new ArrayList<String>();

			for (Object category : source.getCategories()) {
				if (category instanceof Category) {
					categories.add(((Category) category).getLabel());
				}
			}

			post.setCategories(categories);

			return post;
		}
	}
}
