/*
 * Copyright 2014-2015 the original author or authors.
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
package example.springdata.mongodb.textsearch;

import static example.springdata.mongodb.util.ConsoleResultPrinter.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.TextScore;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Integration tests showing the text search functionality using repositories.
 * 
 * @author Christoph Strobl
 * @author Oliver Gierke
 * @author Thomas Darimont
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MongoTestConfiguration.class })
public class TextSearchRepositoryTests {

	@Autowired BlogPostRepository repo;

	/**
	 * Show how to do simple matching. <br />
	 * Note that text search is case insensitive and will also find entries like {@literal releases}.
	 */
	@Test
	public void findAllBlogPostsWithRelease() {

		TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny("release");
		List<BlogPost> blogPosts = repo.findAllBy(criteria);

		printResult(blogPosts, criteria);
	}

	/**
	 * Simple matching using negation.
	 */
	@Test
	public void findAllBlogPostsWithReleaseButHeyIDoWantTheEngineeringStuff() {

		TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny("release").notMatching("engineering");
		List<BlogPost> blogPosts = repo.findAllBy(criteria);

		printResult(blogPosts, criteria);
	}

	/**
	 * Phrase matching looks for the whole phrase as one.
	 */
	@Test
	public void findAllBlogPostsByPhrase() {

		TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingPhrase("release candidate");
		List<BlogPost> blogPosts = repo.findAllBy(criteria);

		printResult(blogPosts, criteria);
	}

	/**
	 * Sort by relevance relying on the value marked with {@link TextScore}.
	 */
	@Test
	public void findAllBlogPostsByPhraseSortByScore() {

		TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingPhrase("release candidate");
		List<BlogPost> blogPosts = repo.findAllByOrderByScoreDesc(criteria);

		printResult(blogPosts, criteria);
	}
}
