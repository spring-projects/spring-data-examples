/*
 * Copyright 2022 the original author or authors.
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
package example.springdata.jpa.hibernatemultitenant.db;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

@Component
public class TenantRoutingDatasource extends AbstractRoutingDataSource {

	@Autowired private TenantIdentifierResolver tenantIdentifierResolver;

	TenantRoutingDatasource() {

		setDefaultTargetDataSource(createEmbeddedDatabase("default"));

		HashMap<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put("VMWARE", createEmbeddedDatabase("VMWARE"));
		targetDataSources.put("PIVOTAL", createEmbeddedDatabase("PIVOTAL"));
		setTargetDataSources(targetDataSources);
	}

	@Override
	protected String determineCurrentLookupKey() {
		return tenantIdentifierResolver.resolveCurrentTenantIdentifier();
	}

	private EmbeddedDatabase createEmbeddedDatabase(String name) {

		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).setName(name).addScript("manual-schema.sql")
				.build();
	}
}
