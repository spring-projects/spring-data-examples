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

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.sun.syndication.feed.atom.Category;
import com.sun.syndication.feed.atom.Content;
import com.sun.syndication.feed.atom.Entry;
import com.sun.syndication.feed.atom.Feed;

import example.springdata.mongodb.textsearch.BlogPost;

/**
 * @author Christoph Strobl
 */
public class BlogPostInitializer implements InitializingBean {

	private final String url;
	private final RestTemplate restTemplate;
	private final Converter<Entry, BlogPost> converter;

	@Autowired MongoTemplate mongoTemplate;

	public BlogPostInitializer(String url) {

		restTemplate = new RestTemplate();
		this.converter = new EntryConverter();
		this.url = url;
	}

	public void initialize(MongoTemplate mongoTemplate) {

		ResponseEntity<Feed> feed = restTemplate.getForEntity(url, Feed.class);
		if (feed.hasBody()) {
			for (Object entry : feed.getBody().getEntries()) {
				if (entry instanceof Entry) {
					mongoTemplate.save(converter.convert((Entry) entry));
				}
			}
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initialize(this.mongoTemplate);
	}

	/**
	 * {@link Converter} implementation capable of converting atom feed {@link Entry} into {@link BlogPost}.
	 * 
	 * @author Christoph Strobl
	 */
	static class EntryConverter implements Converter<Entry, BlogPost> {

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
