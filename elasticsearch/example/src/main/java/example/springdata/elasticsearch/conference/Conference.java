/*
 * Copyright 2020-2021 the original author or authors.
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
package example.springdata.elasticsearch.conference;

import static org.springframework.data.elasticsearch.annotations.FieldType.*;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import lombok.Builder;
import lombok.Data;

/**
 * @author Artur Konczak
 * @author Oliver Gierke
 * @author Christoph Strobl
 */
@Data
@Builder
@Document(indexName = "conference-index")
public class Conference {

	private @Id String id;
	private String name;
	private @Field(type = Date) String date;
	private GeoPoint location;
	private List<String> keywords;

	// do not remove it
	public Conference() {}

	// do not remove it - work around for lombok generated constructor for all params
	public Conference(String id, String name, String date, GeoPoint location, List<String> keywords) {

		this.id = id;
		this.name = name;
		this.date = date;
		this.location = location;
		this.keywords = keywords;
	}
}
