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
package example.springdata.mongodb.textsearch;

import java.util.List;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.TextScore;

/**
 * Document representation of a {@link BlogPost} carrying annotation based information for text indexes.
 * 
 * @author Christoph Strobl
 * @author Oliver Gierke
 */
@Document
@Data
public class BlogPost {

	private @Id String id;
	private @TextIndexed(weight = 3) String title;
	private @TextIndexed(weight = 2) String content;
	private @TextIndexed List<String> categories;
	private @TextScore Float score;

	@Override
	public String toString() {
		return "BlogPost [id=" + id + ", score=" + score + ", title=" + title + ", categories=" + categories + "]";
	}
}
