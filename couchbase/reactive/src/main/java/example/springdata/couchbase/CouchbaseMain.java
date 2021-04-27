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
package example.springdata.couchbase;

import example.springdata.couchbase.model.Airline;
import lombok.RequiredArgsConstructor;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.couchbase.core.CouchbaseTemplate;

import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.manager.query.CreatePrimaryQueryIndexOptions;

/**
 * Main Class of the module
 *
 * @author Denis Rosa
 */
@SpringBootApplication
@RequiredArgsConstructor
public class CouchbaseMain {

	@Autowired CouchbaseTemplate couchbaseTemplate;

	@Autowired Cluster cluster;

	@PostConstruct
	private void postConstruct() {

		cluster.queryIndexes().createPrimaryIndex(couchbaseTemplate.getBucketName(),
				CreatePrimaryQueryIndexOptions.createPrimaryQueryIndexOptions().ignoreIfExists(true));

		// Need to post-process travel data to add _class attribute
		cluster.query("update `travel-sample` set _class='" + Airline.class.getName() + "' where type = 'airline'");

	}
}
