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

import java.util.Collection;

import org.springframework.data.mongodb.core.query.CriteriaDefinition;

import example.springdata.mongodb.textsearch.BlogPost;

/**
 * Just a little helper for showing {@link BlogPost}s output on the console.
 *
 * @author Christoph Strobl
 */
public class ConsoleResultPrinter {

	public static void printResult(Collection<BlogPost> blogPosts, CriteriaDefinition criteria) {

		System.out.println(String.format("XXXXXXXXXXXX -- Found %s blogPosts matching '%s' --XXXXXXXXXXXX",
				blogPosts.size(), criteria != null ? criteria.getCriteriaObject() : ""));

		for (var blogPost : blogPosts) {
			System.out.println(blogPost);
		}

		System.out.println("XXXXXXXXXXXX -- XXXXXXXXXXXX -- XXXXXXXXXXXX\r\n");
	}
}
