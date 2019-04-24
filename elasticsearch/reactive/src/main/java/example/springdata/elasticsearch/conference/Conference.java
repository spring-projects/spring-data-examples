/*
 * Copyright 2019 the original author or authors.
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
package example.springdata.elasticsearch.conference;

import static org.springframework.data.elasticsearch.annotations.FieldType.*;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

/**
 * @auhtor Christoph Strobl
 */
@Data
@Builder
@Document(indexName = "conference-index", type = "geo-class-point-type", shards = 1, replicas = 0,
		refreshInterval = "-1")
public class Conference {

	private @Id String id;
	private String name;
	private @Field(type = Date) String date;
	private GeoPoint location;
	private List<String> keywords;
}
